package com.shagalalab.sozlik.shared.domain.mvi.feature.search

import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import sozlik_cmp.composeapp.generated.resources.Res
import sozlik_cmp.composeapp.generated.resources.suggestion_found
import sozlik_cmp.composeapp.generated.resources.suggestion_not_found

class SearchStore(
    private val repository: DictionaryRepository,
    scope: CoroutineScope = MainScope()
) : Store<SearchAction, SearchState, SearchEffect>(SearchState(), scope), KoinComponent {

    override suspend fun reduce(action: SearchAction, childScope: CoroutineScope): SearchState {
        val oldState = stateFlow.value
        return when (action) {
            is SearchAction.SearchWordAction -> {
                emitState(oldState.copy(isLoading = true))
                if (action.query.isEmpty()) {
                    SearchState()
                } else {
                    try {
                        val result = repository.searchExact(action.query)
                        when {
                            result.isSuccess -> {
                                val searchResult = result.getOrDefault(listOf())
                                if (searchResult.isNotEmpty()) {
                                    oldState.copy(
                                        isLoading = false,
                                        query = action.query,
                                        suggestions = searchResult,
                                        message = null
                                    )
                                } else {
                                    val suggestedResult = repository.searchSimilar(word = action.query)
                                    oldState.copy(
                                        isLoading = false,
                                        query = action.query,
                                        suggestions = suggestedResult,
                                        message = if (suggestedResult.isEmpty()) {
                                            getString(Res.string.suggestion_not_found, action.query)
                                        } else {
                                            getString(Res.string.suggestion_found, action.query)
                                        }
                                    )
                                }
                            }

                            result.isFailure -> {
                                oldState.copy(isLoading = false, errorMessage = "Error: ${result.exceptionOrNull()}", message = null)
                            }

                            else -> oldState.copy(message = null)
                        }
                    } catch (e: Exception) {
                        oldState.copy(isLoading = false, errorMessage = "Error: $e", message = null)
                    }
                }
            }
        }
    }
}

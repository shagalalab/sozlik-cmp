package com.shagalalab.sozlik.shared.domain.mvi.feature.translate

import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent

class TranslationStore(
    private val repository: DictionaryRepository,
    scope: CoroutineScope = MainScope()
) : Store<TranslateAction, TranslateState, TranslateEffect>(TranslateState(), scope), KoinComponent
{
    override suspend fun reduce(action: TranslateAction, childScope: CoroutineScope): TranslateState {
        val oldState = stateFlow.value
        return when(action) {
            is TranslateAction.GetWordByIdAction -> {
                emitState(oldState.copy(isLoading = true))
                getTranslationState(action.id, oldState)
            }
            is TranslateAction.FavoriteWordAction -> {
                try {
                    repository.updateFavorite(action.id, !oldState.isFavorite)
                    getTranslationState(action.id, oldState)
                } catch (e: Exception) {
                    oldState.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
        }
    }

    private suspend fun getTranslationState(id: Long, state: TranslateState): TranslateState {
        return try {
            val result = repository.getTranslation(id)
            when {
                result.isSuccess -> {
                    val dictionary = result.getOrDefault(null)
                    state.copy(isLoading = false, translation = dictionary, isFavorite = dictionary?.isFavorite ?: false)
                }
                result.isFailure -> {
                    state.copy(isLoading = false, errorMessage = "Error: ${result.exceptionOrNull()}")
                }
                else -> state
            }
        } catch (e: Exception) {
            state.copy(isLoading = false, errorMessage = "Error: $e")
        }
    }

}

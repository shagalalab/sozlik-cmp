package com.shagalalab.sozlik.shared.domain.mvi.feature.favorites

import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent

class FavoritesStore(private val repository: DictionaryRepository, scope: CoroutineScope = MainScope()) :
    Store<FavoritesAction, FavoritesState, FavoritesEffect>(FavoritesState(), scope), KoinComponent {
    override suspend fun reduce(action: FavoritesAction, childScope: CoroutineScope): FavoritesState {
        val oldState = stateFlow.value
        return when (action) {
            is FavoritesAction.LoadFavoritesAction -> {
                emitState(oldState.copy(isLoading = true))
                try {
                    val result = repository.getFavorites()
                    when {
                        result.isSuccess -> oldState.copy(isLoading = false, favoriteWords = result.getOrDefault(emptyList()))
                        result.isFailure -> oldState.copy(isLoading = false, errorMessage = "Error: ${result.exceptionOrNull()}")
                        else -> oldState
                    }
                } catch (e: Exception) {
                    oldState.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }

            is FavoritesAction.OnFavoriteWord -> {
                try {
                    repository.updateFavorite(action.id, false)
                    oldState
                } catch (e: Exception) {
                    oldState.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
        }
    }
}

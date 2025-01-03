package com.shagalalab.sozlik.shared.domain.mvi.feature.favorites

import com.shagalalab.sozlik.shared.domain.mvi.base.Action
import com.shagalalab.sozlik.shared.domain.mvi.base.Effect
import com.shagalalab.sozlik.shared.domain.mvi.base.State
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary

sealed interface FavoritesAction: Action {
    data object LoadFavoritesAction: FavoritesAction
    data class OnFavoriteWord(val id: Long): FavoritesAction
}

data class FavoritesState(
    val favoriteWords: List<Dictionary> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
): State

sealed interface FavoritesEffect: Effect

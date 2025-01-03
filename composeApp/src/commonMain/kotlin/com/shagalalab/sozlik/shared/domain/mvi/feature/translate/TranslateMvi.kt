package com.shagalalab.sozlik.shared.domain.mvi.feature.translate

import com.shagalalab.sozlik.shared.domain.mvi.base.Action
import com.shagalalab.sozlik.shared.domain.mvi.base.Effect
import com.shagalalab.sozlik.shared.domain.mvi.base.State
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary

sealed interface TranslateAction : Action {
    data class GetWordByIdAction(val id: Long): TranslateAction
    data class FavoriteWordAction(val id: Long): TranslateAction
}

data class TranslateState(
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val translation: Dictionary? = null,
    val errorMessage: String? = null,
): State

interface TranslateEffect: Effect

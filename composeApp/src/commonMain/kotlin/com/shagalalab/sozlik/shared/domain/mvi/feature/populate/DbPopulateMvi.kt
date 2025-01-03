package com.shagalalab.sozlik.shared.domain.mvi.feature.populate

import com.shagalalab.sozlik.shared.domain.mvi.base.Action
import com.shagalalab.sozlik.shared.domain.mvi.base.Effect
import com.shagalalab.sozlik.shared.domain.mvi.base.State

sealed interface DbPopulateAction : Action {
    data class DbPopulateCheck(val qqen: String?, val ruqq: String?) : DbPopulateAction
}

data class DbPopulateState(val isLoading: Boolean = false, val error: String? = null): State

sealed interface DbPopulateEffect : Effect

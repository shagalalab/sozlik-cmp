package com.shagalalab.sozlik.shared.domain.mvi.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Base class for store, which is a business logic unit. Conceptually, `Store` is similar the same
 * concept of Redux framework, i.e. it will hold the state of the business unit, and does
 * required calculations/operations. Users need to extend this class, and override reduce function,
 * which should generate a new [State] (and emit an [Effect] if needed) based on given [Action].
 */
abstract class Store<A : Action, S : State, E : Effect>(
    defaultState: S,
    private val scope: CoroutineScope
) {
    private val _stateFlow: MutableStateFlow<S> = MutableStateFlow(defaultState)
    private val _effectFlow = MutableSharedFlow<E?>()

    val stateFlow: StateFlow<S> = _stateFlow
    val effectFlow: Flow<E> = _effectFlow.filterNotNull()

    fun dispatch(action: A) {
        scope.launch {
            val newState = withContext(Dispatchers.Default) {
                reduce(action, this)
            }
            emitState(newState)
        }
    }

    protected abstract suspend fun reduce(action: A, childScope: CoroutineScope): S

    protected fun emitState(newState: S) {
        val oldState = _stateFlow.value
        if (oldState != newState) {
            _stateFlow.value = newState
        }
    }

    protected suspend fun emitEffect(effect: E) {
        _effectFlow.emit(effect)
    }
}

package com.shagalalab.sozlik.shared.domain.mvi.base

/**
 * This a parent interface from which all States should derive. State is a final point in
 * unidirectional state flow pattern (along with [Effect]), where we bind the outcome of
 * the state to UI friendly fields.
 */
interface State

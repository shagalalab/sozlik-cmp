package com.shagalalab.sozlik.shared.presentation.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.shagalalab.sozlik.shared.domain.component.flow.SearchFlowComponent
import com.shagalalab.sozlik.shared.presentation.search.SearchScreen
import com.shagalalab.sozlik.shared.presentation.translation.TranslationScreen

@Composable
fun SearchFlowScreen(component: SearchFlowComponent, modifier: Modifier) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack
    ) {
        when (val child = it.instance) {
            is SearchFlowComponent.Child.SearchChild -> SearchScreen(child.component, modifier)
            is SearchFlowComponent.Child.TranslationChild -> TranslationScreen(child.component, modifier)
        }
    }
}

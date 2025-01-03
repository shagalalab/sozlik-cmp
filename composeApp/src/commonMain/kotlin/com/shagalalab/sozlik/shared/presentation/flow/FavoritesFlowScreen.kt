package com.shagalalab.sozlik.shared.presentation.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.shagalalab.sozlik.shared.domain.component.flow.FavoritesFlowComponent
import com.shagalalab.sozlik.shared.presentation.favorites.FavoritesScreen
import com.shagalalab.sozlik.shared.presentation.translation.TranslationScreen

@Composable
fun FavoritesFlowScreen(component: FavoritesFlowComponent, modifier: Modifier) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack
    ) {
        when (val child = it.instance) {
            is FavoritesFlowComponent.Child.FavouritesChild -> FavoritesScreen(child.component, modifier)
            is FavoritesFlowComponent.Child.TranslationChild -> TranslationScreen(child.component, modifier)
        }
    }
}

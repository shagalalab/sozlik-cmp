package com.shagalalab.sozlik.shared.presentation.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.shagalalab.sozlik.shared.domain.component.flow.SettingsFlowComponent
import com.shagalalab.sozlik.shared.presentation.settings.SettingsScreen

@Composable
fun SettingsFlowScreen(component: SettingsFlowComponent, modifier: Modifier) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack
    ) {
        when (val child = it.instance) {
            is SettingsFlowComponent.Child.SettingsChild -> SettingsScreen(child.component, modifier)
        }
    }
}

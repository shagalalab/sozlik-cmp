package com.shagalalab.sozlik.shared

import androidx.compose.runtime.Composable
import com.shagalalab.sozlik.shared.domain.component.root.RootComponent
import com.shagalalab.sozlik.shared.presentation.RootScreen
import com.shagalalab.sozlik.shared.presentation.theme.SozlikTheme

@Composable
fun App(rootComponent: RootComponent) {
    SozlikTheme {
        RootScreen(rootComponent)
    }
}

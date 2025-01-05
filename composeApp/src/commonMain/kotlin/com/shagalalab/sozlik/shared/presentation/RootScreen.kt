package com.shagalalab.sozlik.shared.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.shagalalab.sozlik.resources.Res
import com.shagalalab.sozlik.resources.favorites
import com.shagalalab.sozlik.resources.heart_border
import com.shagalalab.sozlik.resources.search
import com.shagalalab.sozlik.resources.settings
import com.shagalalab.sozlik.shared.domain.component.root.RootComponent
import com.shagalalab.sozlik.shared.presentation.flow.FavoritesFlowScreen
import com.shagalalab.sozlik.shared.presentation.flow.SearchFlowScreen
import com.shagalalab.sozlik.shared.presentation.flow.SettingsFlowScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RootScreen(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()
    val isLoading by component.isLoading.collectAsState(false)
    val activeComponent = childStack.active.instance

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(Res.drawable.search), contentDescription = null) },
                    label = { Text(stringResource(Res.string.search)) },
                    selected = activeComponent is RootComponent.Child.SearchFlowChild,
                    onClick = component::onSearchTabClicked,
                    enabled = !isLoading
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(Res.drawable.heart_border), contentDescription = null) },
                    label = { Text(stringResource(Res.string.favorites)) },
                    selected = activeComponent is RootComponent.Child.FavoritesFlowChild,
                    onClick = component::onFavoritesTabClicked,
                    enabled = !isLoading
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(Res.drawable.settings), contentDescription = null) },
                    label = { Text(stringResource(Res.string.settings)) },
                    selected = activeComponent is RootComponent.Child.SettingsFlowChild,
                    onClick = component::onSettingsTabClicked,
                    enabled = !isLoading
                )
            }
        }
    ) { paddingValue ->
        val padding = Modifier.padding(paddingValue)

        Children(
            stack = childStack
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.SearchFlowChild -> SearchFlowScreen(child.component, padding)
                is RootComponent.Child.FavoritesFlowChild -> FavoritesFlowScreen(child.component, padding)
                is RootComponent.Child.SettingsFlowChild -> SettingsFlowScreen(child.component, padding)
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    LaunchedEffect(Unit) {
        component.checkDbPopulated()
    }
}

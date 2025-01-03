package com.shagalalab.sozlik.shared.domain.component.flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponentImpl
import kotlinx.serialization.Serializable

interface SettingsFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class SettingsChild(val component: SettingsComponent) : Child
    }
}

class SettingsFlowComponentImpl(componentContext: ComponentContext) : SettingsFlowComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = { listOf(Config.Settings) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, SettingsFlowComponent.Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): SettingsFlowComponent.Child =
        when (config) {
            Config.Settings -> SettingsFlowComponent.Child.SettingsChild(
                SettingsComponentImpl(componentContext)
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Settings : Config
    }
}

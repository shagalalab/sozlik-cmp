package com.shagalalab.sozlik.shared.domain.component.flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponent
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponentImpl
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponentImpl
import kotlinx.serialization.Serializable

interface FavoritesFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class FavouritesChild(val component: FavoritesComponent) : Child
        class TranslationChild(val component: TranslationComponent) : Child
    }
}

class FavoritesFlowComponentImpl(componentContext: ComponentContext) : FavoritesFlowComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = { listOf(Config.Favorites) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, FavoritesFlowComponent.Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): FavoritesFlowComponent.Child =
        when (config) {
            Config.Favorites -> FavoritesFlowComponent.Child.FavouritesChild(
                FavoritesComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Translation(it))
                }
            )

            is Config.Translation -> FavoritesFlowComponent.Child.TranslationChild(TranslationComponentImpl(config.id, componentContext) {
                navigation.pop()
            })
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Favorites : Config

        @Serializable
        data class Translation(val id: Long) : Config
    }
}


package com.shagalalab.sozlik.shared.domain.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.shagalalab.sozlik.shared.data.locale.updateLocale
import com.shagalalab.sozlik.shared.domain.component.flow.FavoritesFlowComponent
import com.shagalalab.sozlik.shared.domain.component.flow.FavoritesFlowComponentImpl
import com.shagalalab.sozlik.shared.domain.component.flow.SearchFlowComponent
import com.shagalalab.sozlik.shared.domain.component.flow.SearchFlowComponentImpl
import com.shagalalab.sozlik.shared.domain.component.flow.SettingsFlowComponent
import com.shagalalab.sozlik.shared.domain.component.flow.SettingsFlowComponentImpl
import com.shagalalab.sozlik.shared.domain.mvi.feature.populate.DbPopulateAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.populate.DbPopulateStore
import com.shagalalab.sozlik.shared.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>
    val isLoading: Flow<Boolean>

    fun onSearchTabClicked()
    fun onFavoritesTabClicked()
    fun onSettingsTabClicked()
    fun checkDbPopulated()

    sealed interface Child {
        class SearchFlowChild(val component: SearchFlowComponent) : Child
        class FavoritesFlowChild(val component: FavoritesFlowComponent) : Child
        class SettingsFlowChild(val component: SettingsFlowComponent) : Child
    }
}

class RootComponentImpl(componentContext: ComponentContext) : RootComponent, KoinComponent, ComponentContext by componentContext {
    private val dbPopulateStore: DbPopulateStore by inject()
    private val settingsRepository: SettingsRepository by inject()
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = { listOf(Config.SearchFlow) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    override val isLoading: Flow<Boolean> = dbPopulateStore.stateFlow.map { it.isLoading }

    init {
        // setting locale
        updateLocale(settingsRepository.getSelectedLocale())
    }

    override fun onSearchTabClicked() {
        navigation.bringToFront(Config.SearchFlow)
    }

    override fun onFavoritesTabClicked() {
        navigation.bringToFront(Config.FavoritesFlow)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.SettingsFlow)
    }

    override fun checkDbPopulated() {
        dbPopulateStore.dispatch(DbPopulateAction.DbPopulateCheck)
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.SearchFlow -> RootComponent.Child.SearchFlowChild(
                SearchFlowComponentImpl(componentContext)
            )
            Config.FavoritesFlow -> RootComponent.Child.FavoritesFlowChild(FavoritesFlowComponentImpl(componentContext))
            Config.SettingsFlow -> RootComponent.Child.SettingsFlowChild(SettingsFlowComponentImpl(componentContext))
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object SearchFlow : Config

        @Serializable
        data object FavoritesFlow : Config

        @Serializable
        data object SettingsFlow : Config
    }
}

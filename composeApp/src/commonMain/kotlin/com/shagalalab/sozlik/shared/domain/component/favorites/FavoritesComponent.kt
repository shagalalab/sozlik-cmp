package com.shagalalab.sozlik.shared.domain.component.favorites

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.mvi.feature.favorites.FavoritesAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.favorites.FavoritesState
import com.shagalalab.sozlik.shared.domain.mvi.feature.favorites.FavoritesStore
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface FavoritesComponent {
    val state: StateFlow<FavoritesState>

    fun getFavorites()
    fun onFavoriteItemClicked(id: Long)
    fun onFavoriteClicked(id: Long)
}

class FavoritesComponentImpl(
    componentContext: ComponentContext,
    private val itemClicked: (id: Long) -> Unit
) : ComponentContext by componentContext, FavoritesComponent, KoinComponent {
    private val favoritesStore: FavoritesStore by inject()
    override val state: StateFlow<FavoritesState> = favoritesStore.stateFlow

    override fun getFavorites() {
        favoritesStore.dispatch(FavoritesAction.LoadFavoritesAction)
    }

    override fun onFavoriteItemClicked(id: Long) {
        itemClicked(id)
    }

    override fun onFavoriteClicked(id: Long) {
        favoritesStore.dispatch(FavoritesAction.OnFavoriteWord(id))
        getFavorites()
    }
}

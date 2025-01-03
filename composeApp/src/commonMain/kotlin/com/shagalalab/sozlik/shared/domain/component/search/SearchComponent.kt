package com.shagalalab.sozlik.shared.domain.component.search

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.mvi.feature.search.SearchAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.search.SearchState
import com.shagalalab.sozlik.shared.domain.mvi.feature.search.SearchStore
import com.shagalalab.sozlik.shared.util.componentCoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SearchComponent {
    val query: StateFlow<String>
    val state: StateFlow<SearchState>
    fun onQueryChanged(query: String)
    fun onSearchItemClicked(itemId: Long)
}

class SearchComponentImpl(componentContext: ComponentContext, private val itemClicked: (id: Long) -> Unit, ) : ComponentContext by componentContext, SearchComponent, KoinComponent {
    private val searchStore: SearchStore by inject()

    private val scope = componentCoroutineScope()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override val query = MutableStateFlow("").also { q ->
        q.debounce {
            // no debounce if the query is empty
            if (it.isEmpty()) {
                0
            } else {
                500
            }
        }
            .distinctUntilChanged()
            .mapLatest {
                searchStore.dispatch(SearchAction.SearchWordAction(it))
            }
            .shareIn(scope, SharingStarted.Eagerly)
    }
    override val state = searchStore.stateFlow

    override fun onQueryChanged(query: String) {
        this.query.value = query
    }

    override fun onSearchItemClicked(itemId: Long) {
        itemClicked(itemId)
    }
}

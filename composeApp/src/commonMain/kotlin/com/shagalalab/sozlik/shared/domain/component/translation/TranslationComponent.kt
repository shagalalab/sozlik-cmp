package com.shagalalab.sozlik.shared.domain.component.translation

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslateAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslateState
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslationStore
import com.shagalalab.sozlik.shared.util.ShareManager
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface TranslationComponent {
    val state: StateFlow<TranslateState>

    fun getTranslation()
    fun onShareClick()
    fun onFavoriteClick()
    fun onBackButtonPress()
}

class TranslationComponentImpl(
    private val translationId: Long,
    componentContext: ComponentContext,
    private val onBackPress: () -> Unit,
) : ComponentContext by componentContext, TranslationComponent, KoinComponent {
    private val translationStore: TranslationStore by inject()
    private val shareManager: ShareManager by inject()

    override val state: StateFlow<TranslateState> = translationStore.stateFlow

    override fun getTranslation() {
        translationStore.dispatch(TranslateAction.GetWordByIdAction(translationId))
    }

    override fun onShareClick() {
        state.value.translation?.let {
            val t = it.translation.replace(Regex("</?[a-z]>"), "")
            shareManager.shareTranslation("${it.word}: $t - https://sozlik.com")
        }
    }

    override fun onFavoriteClick() {
        translationStore.dispatch(TranslateAction.FavoriteWordAction(translationId))
    }

    override fun onBackButtonPress() {
        onBackPress()
    }
}

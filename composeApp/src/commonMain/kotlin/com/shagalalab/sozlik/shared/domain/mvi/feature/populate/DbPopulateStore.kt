package com.shagalalab.sozlik.shared.domain.mvi.feature.populate

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.shagalalab.sozlik.shared.data.datastore.model.DictionaryDeserialized
import com.shagalalab.sozlik.shared.data.keyvalue.DbPopulatedKeyValue
import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryType
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.serialization.json.Json

class DbPopulateStore(
    private val keyValue: DbPopulatedKeyValue,
    private val repository: DictionaryRepository,
    scope: CoroutineScope = MainScope()
) : Store<DbPopulateAction, DbPopulateState, DbPopulateEffect>(DbPopulateState(), scope) {
    override suspend fun reduce(action: DbPopulateAction, childScope: CoroutineScope): DbPopulateState {
        val oldState = stateFlow.value
        return when (action) {
            is DbPopulateAction.DbPopulateCheck -> {
                if (keyValue.isDbPopulated) {
                    oldState
                } else {
                    emitState(oldState.copy(isLoading = true))
                    val qqEn = action.qqen
                    val ruQq = action.ruqq
                    if (qqEn != null && ruQq != null) {
                        try {
                            val qqEnDeserialized = Json.decodeFromString<List<DictionaryDeserialized>>(qqEn)
                            val ruQqDeserialized = Json.decodeFromString<List<DictionaryDeserialized>>(ruQq)
                            val qqEnDomain = qqEnDeserialized.map { dict ->
                                Dictionary(
                                    id = 0L,
                                    type = DictionaryType.QQ_EN,
                                    word = dict.word,
                                    translation = dict.translation
                                )
                            }
                            repository.insert(qqEnDomain)

                            val ruQqDomain = ruQqDeserialized.map { dict ->
                                Dictionary(
                                    id = 0L,
                                    type = DictionaryType.RU_QQ,
                                    word = dict.word.toLowerCase(Locale.current).replace("//", ""),
                                    translation = dict.translation,
                                    rawWord = dict.word
                                )
                            }
                            repository.insert(ruQqDomain)

                            keyValue.updateDbPopulated()
                            oldState.copy(isLoading = false)
                        } catch (e: Exception) {
                            oldState.copy(isLoading = false, error = e.message)
                        }
                    } else {
                        oldState.copy(isLoading = false)
                    }
                }
            }
        }
    }
}

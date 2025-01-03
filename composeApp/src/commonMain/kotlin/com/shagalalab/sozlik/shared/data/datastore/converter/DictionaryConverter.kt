package com.shagalalab.sozlik.shared.data.datastore.converter

import com.shagalalab.sozlik.DictionaryDb
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryType

internal fun DictionaryDb.toDomain(): Dictionary {
    return Dictionary(
        id = id,
        type = DictionaryType.from(type),
        word = word,
        rawWord = raw_word,
        translation = translation,
        isFavorite = is_favorite,
        lastAccessed = last_accessed
    )
}

internal fun Dictionary.toDb(): DictionaryDb {
    return DictionaryDb(
        id = id,
        type = type.index,
        word = word,
        raw_word = rawWord,
        translation = translation,
        is_favorite = isFavorite,
        last_accessed = lastAccessed
    )
}


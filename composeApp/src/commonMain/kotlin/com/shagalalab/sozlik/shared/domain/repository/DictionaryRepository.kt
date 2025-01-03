package com.shagalalab.sozlik.shared.domain.repository

import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryBase

interface DictionaryRepository {
    suspend fun getTranslation(id: Long): Result<Dictionary>
    suspend fun getFavorites(): Result<List<Dictionary>>
    suspend fun getHistory(): Result<List<Dictionary>>
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)
    suspend fun updateLastAccessTime(id: Long, lastAccessed: Long)
    suspend fun insert(data: List<Dictionary>)
    suspend fun searchExact(word: String): Result<List<DictionaryBase>>
    suspend fun searchSimilar(word: String): List<DictionaryBase>
}

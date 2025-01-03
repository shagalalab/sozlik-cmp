package com.shagalalab.sozlik.shared.data.repository

import com.shagalalab.sozlik.shared.data.asResult
import com.shagalalab.sozlik.shared.data.datastore.LocalDataStore
import com.shagalalab.sozlik.shared.data.datastore.converter.toDb
import com.shagalalab.sozlik.shared.data.datastore.converter.toDomain
import com.shagalalab.sozlik.shared.data.spellchecker.SpellChecker
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryBase
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryType
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(private val dataStore: LocalDataStore, private val spellChecker: SpellChecker): DictionaryRepository {
    /**
     * Technically, we don't need [Map] structure here, just pure [List] with [String] would be enough. However,
     * in "Did you mean xyz" feature, we do permutations of the query, and it might result in thousands of
     * options, and we need to check if any of the permutations are real words from our dictionary. Here
     * Map shows its prime, by allowing us to do a fast lookup. Without Map, if use List for example, it'd
     * return results much much slower (counted in 10x).
     */
    private val allWords: Map<String, Long> by lazy {
        val wordHashMap: MutableMap<String, Long> = mutableMapOf()
        dataStore.getAllItems().map {
            wordHashMap[it.word] = it.id
        }
        return@lazy wordHashMap
    }

    override suspend fun getTranslation(id: Long): Result<Dictionary> {
        return asResult { dataStore.getItem(id).toDomain() }
    }

    override suspend fun getFavorites(): Result<List<Dictionary>> {
        return asResult { dataStore.getFavorites().map { it.toDomain() } }
    }

    override suspend fun getHistory(): Result<List<Dictionary>> {
        return asResult { dataStore.getLastAccessed().map { it.toDomain() } }
    }

    override suspend fun updateFavorite(id: Long, isFavorite: Boolean) {
        dataStore.updateFavorite(id, isFavorite)
    }

    override suspend fun updateLastAccessTime(id: Long, lastAccessed: Long) {
        dataStore.updateLastAccessTime(id, lastAccessed)
    }

    override suspend fun insert(data: List<Dictionary>) {
        dataStore.insertData(data.map { it.toDb() })
    }

    override suspend fun searchExact(word: String): Result<List<DictionaryBase>> {
        return if (word.isEmpty()) {
            asResult { listOf() }
        } else {
            asResult {
                dataStore.getItemsLike(word).map {
                    DictionaryBase(it.id, DictionaryType.from(it.type), it.word)
                }
            }
        }
    }

    override suspend fun searchSimilar(word: String): List<DictionaryBase> {
        val alternatives: MutableList<DictionaryBase> = mutableListOf()

        val permutations = spellChecker.edits1(word)
        for (permutation in permutations) {
            if (allWords.containsKey(permutation)) {
                dataStore.getItem(permutation)?.let {
                    alternatives.add(DictionaryBase(it.id, DictionaryType.from(it.type), it.word))
                }
            }
        }
        return alternatives.sortedWith { a, b -> a.word.compareTo(b.word) }
    }
}

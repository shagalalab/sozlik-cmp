package com.shagalalab.sozlik.shared.data.datastore

import com.shagalalab.sozlik.DictionaryDb
import com.shagalalab.sozlik.SelectAll
import com.shagalalab.sozlik.SelectByWord
import com.shagalalab.sozlik.SelectLike
import com.shagalalab.sozlik.SozlikDatabase

class LocalDataStore(private val database: SozlikDatabase) {
    fun getAllItems(): List<SelectAll> = database.dictionaryDbQueries.selectAll().executeAsList()

    fun getItem(word: String): SelectByWord? {
        return database.dictionaryDbQueries.selectByWord(word).executeAsOneOrNull()
    }

    fun getItem(id: Long): DictionaryDb {
        return database.dictionaryDbQueries.selectById(id).executeAsOne()
    }

    fun getItemsLike(word: String): List<SelectLike> {
        return database.dictionaryDbQueries.selectLike(word).executeAsList()
    }

    fun getLastAccessed(): List<DictionaryDb> {
        return database.dictionaryDbQueries.selectLastAccessed().executeAsList()
    }

    fun getFavorites(): List<DictionaryDb> {
        return database.dictionaryDbQueries.selectFavorites().executeAsList()
    }

    fun updateFavorite(id: Long, isFavorite: Boolean) {
        database.dictionaryDbQueries.updateFavorite(isFavorite, id)
    }

    fun updateLastAccessTime(id: Long, accessTime: Long) {
        database.dictionaryDbQueries.updateLastAccessTime(accessTime, id)
    }

    fun insertData(entries: List<DictionaryDb>) {
        database.dictionaryDbQueries.transaction {
            entries.forEach { entry ->
                database.dictionaryDbQueries.insert(entry.type, entry.word, entry.raw_word, entry.translation)
            }
        }
    }
}

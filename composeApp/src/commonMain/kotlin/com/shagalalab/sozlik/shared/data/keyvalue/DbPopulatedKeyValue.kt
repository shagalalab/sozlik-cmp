package com.shagalalab.sozlik.shared.data.keyvalue

import com.russhwolf.settings.Settings

interface DbPopulatedKeyValue {
    val isDbPopulated: Boolean
    fun updateDbPopulated()
}

internal class DbPopulatedKeyValueImpl(private val settings: Settings): DbPopulatedKeyValue {
    companion object {
        private const val KEY_DB_POPULATED = "KEY_DB_POPULATED"
    }

    override val isDbPopulated: Boolean
        get() = settings.getBoolean(KEY_DB_POPULATED, false)

    override fun updateDbPopulated() {
        settings.putBoolean(KEY_DB_POPULATED, true)
    }
}

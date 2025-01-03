package com.shagalalab.sozlik.shared.data.datastore

import com.shagalalab.sozlik.SozlikDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

internal class JvmSqldelightDriver : SqldelightDriver {
    override val driver: SqlDriver
        get() {
            val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:sozlik.db")
            SozlikDatabase.Schema.create(driver)
            return driver
        }
}

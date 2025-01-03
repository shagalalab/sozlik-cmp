package com.shagalalab.sozlik.shared.data.datastore

import android.content.Context
import com.shagalalab.sozlik.SozlikDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal class AndroidSqldelightDriver(context: Context) : SqldelightDriver {
    override val driver: SqlDriver = AndroidSqliteDriver(SozlikDatabase.Schema, context, "sozlik.db")
}

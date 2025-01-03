package com.shagalalab.sozlik.shared.data.datastore

import com.shagalalab.sozlik.SozlikDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

internal class AppleSqldelightDriver : SqldelightDriver {
    override val driver: SqlDriver = NativeSqliteDriver(SozlikDatabase.Schema, "sozlik.db")
}

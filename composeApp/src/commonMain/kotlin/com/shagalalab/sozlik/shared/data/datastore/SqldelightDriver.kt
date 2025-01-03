package com.shagalalab.sozlik.shared.data.datastore

import com.squareup.sqldelight.db.SqlDriver

internal interface SqldelightDriver {
    val driver: SqlDriver
}

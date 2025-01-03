package com.shagalalab.sozlik.shared.di

import com.shagalalab.sozlik.shared.data.datastore.JvmSqldelightDriver
import com.shagalalab.sozlik.shared.data.datastore.SqldelightDriver
import com.shagalalab.sozlik.shared.util.DesktopShareManager
import com.shagalalab.sozlik.shared.util.ShareManager
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<SqldelightDriver> { JvmSqldelightDriver() }
    single<ShareManager> { DesktopShareManager() }
}

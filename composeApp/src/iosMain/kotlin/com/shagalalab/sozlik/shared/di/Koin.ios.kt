package com.shagalalab.sozlik.shared.di

import com.shagalalab.sozlik.shared.data.datastore.AppleSqldelightDriver
import com.shagalalab.sozlik.shared.data.datastore.SqldelightDriver
import com.shagalalab.sozlik.shared.util.AppleShareManager
import com.shagalalab.sozlik.shared.util.ShareManager
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin() = initKoin { }

internal actual val platformModule: Module = module {
    single<SqldelightDriver> { AppleSqldelightDriver() }
    single<ShareManager> { AppleShareManager() }
}

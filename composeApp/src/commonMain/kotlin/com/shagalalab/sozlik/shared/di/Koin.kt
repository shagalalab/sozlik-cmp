package com.shagalalab.sozlik.shared.di

import com.russhwolf.settings.Settings
import com.shagalalab.sozlik.SozlikDatabase
import com.shagalalab.sozlik.shared.data.datastore.LocalDataStore
import com.shagalalab.sozlik.shared.data.datastore.SqldelightDriver
import com.shagalalab.sozlik.shared.data.keyvalue.DbPopulatedKeyValue
import com.shagalalab.sozlik.shared.data.keyvalue.DbPopulatedKeyValueImpl
import com.shagalalab.sozlik.shared.data.repository.DictionaryRepositoryImpl
import com.shagalalab.sozlik.shared.data.repository.SettingsRepositoryImpl
import com.shagalalab.sozlik.shared.data.spellchecker.SpellChecker
import com.shagalalab.sozlik.shared.domain.mvi.feature.favorites.FavoritesStore
import com.shagalalab.sozlik.shared.domain.mvi.feature.populate.DbPopulateStore
import com.shagalalab.sozlik.shared.domain.mvi.feature.search.SearchStore
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslationStore
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import com.shagalalab.sozlik.shared.domain.repository.SettingsRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(allModules)
    }

private val allModules: Module
    get() = module {
        includes(commonModule, platformModule)
    }

private val commonModule = module {
    single { SozlikDatabase(get<SqldelightDriver>().driver) }
    single { LocalDataStore(get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get(), get()) }
    single { Settings() }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<DbPopulatedKeyValue> { DbPopulatedKeyValueImpl(get()) }
    single { DbPopulateStore(get(), get()) }
    single { SearchStore(get()) }
    single { TranslationStore(get()) }
    single { FavoritesStore(get()) }
    single { SpellChecker() }
}

internal expect val platformModule: Module

package com.shagalalab.sozlik

import android.app.Application
import com.shagalalab.sozlik.shared.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class SozlikApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@SozlikApplication)
        }
    }
}

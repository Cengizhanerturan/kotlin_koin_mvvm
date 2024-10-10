package com.gcyazilim.koincryptocrazy

import android.app.Application
import com.gcyazilim.koincryptocrazy.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
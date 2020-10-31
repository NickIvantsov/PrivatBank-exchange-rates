package com.example.android.privatbank_exchange_rates.app

import android.app.Application
import com.example.android.privatbank_exchange_rates.app.di.appMode
import com.example.android.privatbank_exchange_rates.app.di.repositoryModule
import com.example.android.privatbank_exchange_rates.app.di.viewModelModule
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(androidContext = this, listOf(appMode, repositoryModule,viewModelModule))
    }
}
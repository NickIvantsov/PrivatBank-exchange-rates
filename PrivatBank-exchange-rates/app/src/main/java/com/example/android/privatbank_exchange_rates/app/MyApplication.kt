package com.example.android.privatbank_exchange_rates.app

import android.app.Application
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.android.privatbank_exchange_rates.BuildConfig
import com.example.android.privatbank_exchange_rates.app.di.appMode
import com.example.android.privatbank_exchange_rates.app.di.repositoryModule
import com.example.android.privatbank_exchange_rates.app.di.viewModelModule
import com.example.android.privatbank_exchange_rates.util.RealeseTree
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MyApplication : Application() {

    val isConnectedLiveLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(androidContext = this, listOf(appMode, repositoryModule, viewModelModule))
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(RealeseTree())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkListenerForN()
        }
    }

    private fun networkListenerForN() {
        applicationContext?.let { thisApplicationContext ->
            ContextCompat.getSystemService(
                thisApplicationContext,
                ConnectivityManager::class.java
            )?.let { connMgr ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    connMgr.registerDefaultNetworkCallback(object :
                        ConnectivityManager.NetworkCallback() {
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            isConnectedLiveLiveData.postValue(true)
                            printLog("onAvailable()")
                        }

                        override fun onLosing(network: Network, maxMsToLive: Int) {
                            super.onLosing(network, maxMsToLive)
                            printLog("onLosing()")
                        }

                        override fun onLost(network: Network) {
                            super.onLost(network)
                            isConnectedLiveLiveData.postValue(false)
                            printLog("onLost()")
                        }

                        override fun onUnavailable() {
                            super.onUnavailable()
                            printLog("onUnavailable()")
                        }

                        override fun onCapabilitiesChanged(
                            network: Network,
                            networkCapabilities: NetworkCapabilities
                        ) {
                            super.onCapabilitiesChanged(network, networkCapabilities)
                            printLog("onCapabilitiesChanged()")
                        }

                        override fun onLinkPropertiesChanged(
                            network: Network,
                            linkProperties: LinkProperties
                        ) {
                            super.onLinkPropertiesChanged(network, linkProperties)
                            printLog("onLinkPropertiesChanged()")
                        }

                        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                            super.onBlockedStatusChanged(network, blocked)
                            printLog("onBlockedStatusChanged()")
                        }
                    })
                }
            }

        }
    }

    private fun printLog(msg: String) {
        Timber.d("$msg; Thread name = ${Thread.currentThread().name}")
    }

}
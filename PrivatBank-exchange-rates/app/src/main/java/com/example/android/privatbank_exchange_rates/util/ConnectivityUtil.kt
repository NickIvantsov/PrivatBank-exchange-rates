package com.example.android.privatbank_exchange_rates.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import com.example.android.privatbank_exchange_rates.app.MyApplication
import timber.log.Timber

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive ${Build.VERSION.SDK_INT}")
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isWifiConn: Boolean = false
        var isMobileConn: Boolean = false
        conn.allNetworks.forEach { network ->
            conn.getNetworkInfo(network).apply {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = isWifiConn or isConnected
                }
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = isMobileConn or isConnected
                }
            }
        }
        try {
            val application = (context.applicationContext as MyApplication)
            application.isConnectedLiveLiveData.postValue(isWifiConn || isMobileConn)
        } catch (ex: Throwable) {
            Timber.e(ex)
        }
    }

}
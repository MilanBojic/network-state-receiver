package com.networkmodule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.reactivex.subjects.PublishSubject


open class NetworkStateReceiver(val context: Context) : BroadcastReceiver() {

    private val UNKNOWN_ERROR = -999
    private val NONE = -1
    var publishSubjectNetworking: PublishSubject<Int> = PublishSubject.create<Int>()

    enum class ENetworkType {
        WIFI,
        MOBILE,
        NONE,
        UNKNOWN
    }

    private var connectivityManager: ConnectivityManager

    init {
        context.registerReceiver(this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        publishSubjectNetworking.onNext(UNKNOWN_ERROR)
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    }


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.extras == null)
            return

        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED) {
            publishSubjectNetworking.onNext(networkInfo.type)

        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)) {
            publishSubjectNetworking.onNext(NONE)
        }

    }


    fun networkSwitcher(type: Int): ENetworkType {
        when (type) {
            0 -> return ENetworkType.MOBILE
            1 -> return ENetworkType.WIFI
            -1 -> return ENetworkType.NONE
            else -> ENetworkType.UNKNOWN;
        }
        return ENetworkType.NONE
    }

    fun isNetworkConnected() = connectivityManager.activeNetworkInfo.isConnected

    fun destroy() {
        context.unregisterReceiver(this)
    }


}

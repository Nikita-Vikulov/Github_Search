package com.example.githubsearch.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.githubsearch.domain.interfaces.INetworkStatus

class AndroidNetworkStatus(context: Context) : INetworkStatus {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

    override fun isNetworkAvailableNow(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }
}

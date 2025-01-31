package com.mzaragozaserrano.data.repositories.remote

import android.content.Context
import android.net.ConnectivityManager
import com.mzaragozaserrano.domain.repositories.remote.NetworkRepository

class NetworkRepositoryImpl(private val context: Context) : NetworkRepository {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }

}
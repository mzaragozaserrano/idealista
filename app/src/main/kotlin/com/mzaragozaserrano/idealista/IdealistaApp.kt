package com.mzaragozaserrano.idealista

import android.app.Application
import com.mzaragozaserrano.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IdealistaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@IdealistaApp)
            modules(dataModule)
        }
    }

}
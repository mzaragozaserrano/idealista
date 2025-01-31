package com.mzaragozaserrano.idealista

import android.app.Application
import com.mzaragozaserrano.data.di.dataModule
import com.mzaragozaserrano.domain.di.domainModule
import com.mzaragozaserrano.presentation.di.presentationModule
import com.mzs.core.data.di.coreDataModule
import com.mzs.core.domain.di.coreDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IdealistaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@IdealistaApp)
            modules(coreDataModule, coreDomainModule, dataModule, domainModule, presentationModule)
        }
    }

}
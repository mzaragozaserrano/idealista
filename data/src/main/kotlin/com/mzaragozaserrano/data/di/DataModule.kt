package com.mzaragozaserrano.data.di

import com.mzaragozaserrano.data.datasources.remote.AdsDataSource
import com.mzaragozaserrano.data.datasources.remote.AdsDataSourceImpl
import com.mzaragozaserrano.data.repositories.remote.AdsRepositoryImpl
import com.mzaragozaserrano.domain.repositories.remote.AdsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    // DataSources
    singleOf(::AdsDataSourceImpl) { bind<AdsDataSource>() }
    // Repositories
    singleOf(::AdsRepositoryImpl) { bind<AdsRepository>() }
}

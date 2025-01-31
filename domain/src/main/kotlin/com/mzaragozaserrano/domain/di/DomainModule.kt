package com.mzaragozaserrano.domain.di

import com.mzaragozaserrano.domain.usecases.GetAllAdsUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetDetailedAdUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    // UseCases
    factoryOf(::GetDetailedAdUseCaseImpl)
    factoryOf(::GetAllAdsUseCaseImpl)
}
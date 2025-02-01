package com.mzaragozaserrano.domain.di

import com.mzaragozaserrano.domain.usecases.AddFavoriteAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetAllAdsUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetAllFavoritesUseCaseImpl
import com.mzaragozaserrano.domain.usecases.GetDetailedAdUseCaseImpl
import com.mzaragozaserrano.domain.usecases.RemoveFavoriteAdUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    // UseCases
    factoryOf(::AddFavoriteAdUseCaseImpl)
    factoryOf(::GetDetailedAdUseCaseImpl)
    factoryOf(::GetAllAdsUseCaseImpl)
    factoryOf(::GetAllFavoritesUseCaseImpl)
    factoryOf(::RemoveFavoriteAdUseCaseImpl)
}
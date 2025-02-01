package com.mzaragozaserrano.data.di

import android.content.Context
import androidx.room.Room
import com.mzaragozaserrano.data.datasources.local.database.FavoritesDataSource
import com.mzaragozaserrano.data.datasources.local.database.FavoritesDatabase
import com.mzaragozaserrano.data.datasources.remote.AdsDataSource
import com.mzaragozaserrano.data.datasources.remote.AdsDataSourceImpl
import com.mzaragozaserrano.data.repositories.local.FavoritesRepositoryImpl
import com.mzaragozaserrano.data.repositories.remote.AdsRepositoryImpl
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzaragozaserrano.domain.repositories.remote.AdsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun provideFavoritesDatabase(context: Context): FavoritesDatabase =
    Room.databaseBuilder(
        context = context,
        klass = FavoritesDatabase::class.java,
        name = "FavoritesDB"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

fun provideFavoritesDataSource(favoritesDatabase: FavoritesDatabase): FavoritesDataSource =
    favoritesDatabase.favoritesDataSource()

val dataModule = module {
    // Database
    single { provideFavoritesDatabase(context = androidContext()) }
    single { provideFavoritesDataSource(favoritesDatabase = get()) }
    // DataSources
    singleOf(::AdsDataSourceImpl) { bind<AdsDataSource>() }
    // Repositories
    singleOf(::AdsRepositoryImpl) { bind<AdsRepository>() }
    singleOf(::FavoritesRepositoryImpl) { bind<FavoritesRepository>() }
}

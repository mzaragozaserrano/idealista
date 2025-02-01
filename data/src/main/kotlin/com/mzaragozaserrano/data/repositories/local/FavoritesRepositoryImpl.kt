package com.mzaragozaserrano.data.repositories.local

import android.util.Log
import com.mzaragozaserrano.data.datasources.local.database.FavoritesDataSource
import com.mzaragozaserrano.data.utils.transform
import com.mzaragozaserrano.domain.bo.FavoriteAdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzs.core.domain.utils.generic.DateUtils

class FavoritesRepositoryImpl(
    private val favoritesDataSource: FavoritesDataSource,
    private val dateUtils: DateUtils,
) : FavoritesRepository {

    override fun addFavorite(ad: FavoriteAdBO): Boolean {
        Log.d("hola", "adding - $ad")
        favoritesDataSource.addFavorite(ad = ad.transform(dateUtils = dateUtils))
        return true
    }

    override fun getAllFavorites(): List<FavoriteAdBO> {
        val result = mutableListOf<FavoriteAdBO>()
        Log.d("hola", "list - ${favoritesDataSource.getAllFavorites()}")
        favoritesDataSource.getAllFavorites().forEach { ad ->
            result.add(ad.transform())
        }
        return result
    }

    override fun removeFavorite(ad: FavoriteAdBO): Boolean {
        Log.d("hola", "removing - $ad")
        favoritesDataSource.removeFavorite(ad = ad.transform())
        return true
    }

}
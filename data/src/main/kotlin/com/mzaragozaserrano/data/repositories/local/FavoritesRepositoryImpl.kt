package com.mzaragozaserrano.data.repositories.local

import com.mzaragozaserrano.data.datasources.local.database.FavoritesDataSource
import com.mzaragozaserrano.data.utils.transform
import com.mzaragozaserrano.domain.bo.AdBO
import com.mzaragozaserrano.domain.repositories.local.FavoritesRepository
import com.mzs.core.data.datasources.local.ResourcesDataSource
import com.mzs.core.domain.utils.generic.DateUtils

class FavoritesRepositoryImpl(
    private val favoritesDataSource: FavoritesDataSource,
    private val resourcesDataSource: ResourcesDataSource,
    private val dateUtils: DateUtils,
) : FavoritesRepository {

    override fun addFavorite(ad: AdBO): Boolean {
        favoritesDataSource.addFavorite(
            ad = ad.transform(
                dateUtils = dateUtils,
                resourcesDataSource = resourcesDataSource
            )
        )
        return true
    }

    override fun getAllFavorites(): List<AdBO> {
        val result = mutableListOf<AdBO>()
        favoritesDataSource.getAllFavorites().forEach { ad ->
            result.add(ad.transform(resourcesDataSource = resourcesDataSource))
        }
        return result
    }

    override fun removeFavorite(ad: AdBO): Boolean {
        favoritesDataSource.removeFavorite(ad = ad.transform(resourcesDataSource = resourcesDataSource))
        return true
    }

}
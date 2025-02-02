package com.mzaragozaserrano.domain.repositories.local

import com.mzaragozaserrano.domain.bo.AdBO

interface FavoritesRepository {
    fun addFavorite(ad: AdBO): Boolean
    fun getAllFavorites(): List<AdBO>
    fun removeFavorite(ad: AdBO): Boolean
}
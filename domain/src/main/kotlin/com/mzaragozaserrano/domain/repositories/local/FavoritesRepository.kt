package com.mzaragozaserrano.domain.repositories.local

import com.mzaragozaserrano.domain.bo.FavoriteAdBO

interface FavoritesRepository {
    fun addFavorite(ad: FavoriteAdBO): Boolean
    fun getAllFavorites(): List<FavoriteAdBO>
    fun removeFavorite(ad: FavoriteAdBO): Boolean
}
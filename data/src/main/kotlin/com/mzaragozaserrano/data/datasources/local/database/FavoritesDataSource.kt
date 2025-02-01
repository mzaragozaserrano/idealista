package com.mzaragozaserrano.data.datasources.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mzaragozaserrano.data.dto.FavoriteAdDTO

@Dao
interface FavoritesDataSource {
    @Insert
    fun addFavorite(ad: FavoriteAdDTO)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): List<FavoriteAdDTO>

    @Delete
    fun removeFavorite(ad: FavoriteAdDTO)
}
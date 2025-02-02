package com.mzaragozaserrano.data.datasources.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mzaragozaserrano.data.dto.AdDTO

@Dao
interface FavoritesDataSource {
    @Insert
    fun addFavorite(ad: AdDTO)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): List<AdDTO>

    @Query("SELECT * FROM favorite_table WHERE propertyCode = :propertyCode")
    fun getAdById(propertyCode: String): AdDTO?

    @Delete
    fun removeFavorite(ad: AdDTO)
}
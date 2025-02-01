package com.mzaragozaserrano.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mzaragozaserrano.data.dto.FavoriteAdDTO

@Database(entities = [FavoriteAdDTO::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDataSource(): FavoritesDataSource
}
package com.mzaragozaserrano.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mzaragozaserrano.data.dto.AdDTO
import com.mzaragozaserrano.data.dto.Converters

@Database(entities = [AdDTO::class], version = 1)
@TypeConverters(Converters::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDataSource(): FavoritesDataSource
}
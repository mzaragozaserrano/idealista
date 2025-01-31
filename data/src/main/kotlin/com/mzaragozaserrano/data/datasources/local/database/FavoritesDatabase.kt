package com.mzaragozaserrano.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thecocktailapp.data.dto.DrinkDTO

@Database(entities = [DrinkDTO::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDataSource(): FavoritesDataSource
}

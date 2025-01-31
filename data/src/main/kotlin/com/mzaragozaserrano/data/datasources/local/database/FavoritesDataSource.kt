/*
package com.thecocktailapp.data.datasources.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.thecocktailapp.data.dto.DrinkDTO

@Dao
interface FavoritesDataSource {
    @Insert
    fun addDrink(drink: DrinkDTO)

    @Query("SELECT EXISTS(SELECT 1 FROM drink_table WHERE idDrink = :id)")
    fun isFavoriteDrink(id: Int): Boolean

    @Query("SELECT * FROM drink_table")
    fun getAllFavorites(): List<DrinkDTO>

    @Delete
    fun removeDrink(drink: DrinkDTO)
}*/

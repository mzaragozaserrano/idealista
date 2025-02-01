package com.mzaragozaserrano.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteAdDTO(
    @PrimaryKey val id: Int = 0,
    val date: String,
    val operation: String?,
    val price: String?,
    val subtitle: String?,
    val thumbnail: String?,
    val title: String?,
)
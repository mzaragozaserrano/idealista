package com.mzaragozaserrano.domain.bo

data class FavoriteAdBO(
    val id: String,
    val date: String? = null,
    val price: String,
    val title: String,
)
package com.mzaragozaserrano.domain.bo

data class FavoriteAdBO(
    val id: String?,
    val date: String? = null,
    val price: String?,
    val subtitle: String?,
    val title: String?,
    val thumbnail: String?,
    val operation: String?
)
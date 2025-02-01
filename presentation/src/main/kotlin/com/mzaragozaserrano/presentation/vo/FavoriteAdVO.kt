package com.mzaragozaserrano.presentation.vo

data class FavoriteAdVO(
    val id: String,
    val date: String? = null,
    val price: String,
    val subtitle: String,
    val title: String,
    val thumbnail: String,
    val type: AdType?
)
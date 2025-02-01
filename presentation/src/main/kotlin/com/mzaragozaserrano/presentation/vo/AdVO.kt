package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import java.io.Serializable

data class AdVO(
    val extraInfo: List<Info>,
    val features: List<Feature>,
    val hasNotInformation: Boolean,
    val id: String,
    var isFavorite: Boolean,
    @StringRes val prefixTitle: Int?,
    val price: String,
    val subtitle: String,
    val thumbnail: String,
    val title: String,
    val type: AdType?,
) : Serializable
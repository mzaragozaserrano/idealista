package com.mzaragozaserrano.presentation.vo

import com.mzaragozaserrano.domain.bo.StringResource
import java.io.Serializable

data class AdVO(
    val currencySuffix: String,
    val date: String? = null,
    val extraInfo: List<Info>,
    val features: List<Feature>,
    val hasNotInformation: Boolean,
    val id: String,
    var isFavorite: Boolean,
    val prefixTitle: StringResource?,
    val price: String,
    val subtitle: String,
    val thumbnail: String,
    val title: String,
    val type: AdType?,
) : Serializable
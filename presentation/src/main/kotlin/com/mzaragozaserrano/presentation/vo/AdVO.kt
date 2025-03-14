package com.mzaragozaserrano.presentation.vo

import com.mzaragozaserrano.domain.utils.DomainStringResource
import java.io.Serializable

data class AdVO(
    val currencySuffix: String,
    val date: String? = null,
    val extraInfo: List<Info>,
    val features: List<Feature>,
    val hasNotInformation: Boolean,
    val id: String,
    var isFavorite: Boolean,
    val latitude: Double? = null,
    val longitude: Double?,
    val prefixTitle: DomainStringResource?,
    val price: String,
    val subtitle: String,
    val thumbnail: String,
    val title: String,
    val type: AdType?,
) : Serializable
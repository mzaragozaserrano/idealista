package com.mzaragozaserrano.presentation.vo

import com.mzs.core.presentation.components.compose.utils.Skeletonable

data class DetailedAdVO(
    val titleId: Skeletonable<Int> = Skeletonable(),
    val description: Skeletonable<String> = Skeletonable(),
    val latitude: Double? = null,
    val longitude: Double? = null,
    val multimedia: List<String>? = null,
    val tags: List<Info>? = null,
)
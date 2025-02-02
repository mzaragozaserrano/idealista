package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R
import java.io.Serializable

sealed class Filter(@StringRes val textId: Int) : Serializable {
    data object All : Filter(textId = R.string.filter_all), Serializable
    data class Rent(override val type: AdType = AdType.Rent) : Filter(textId = AdType.Rent.textId),
        Type, Serializable

    data class Sale(override val type: AdType = AdType.Sale) : Filter(textId = AdType.Sale.textId),
        Type, Serializable
}

sealed interface Type {
    val type: AdType
}
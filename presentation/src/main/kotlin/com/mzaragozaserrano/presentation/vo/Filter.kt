package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R

sealed class Filter(@StringRes val textId: Int) {
    data object All : Filter(textId = R.string.filter_all)
    data class Rent(override val type: AdType = AdType.Rent) : Filter(textId = AdType.Rent.textId),
        Type

    data class Sale(override val type: AdType = AdType.Sale) : Filter(textId = AdType.Sale.textId),
        Type
}

sealed interface Type {
    val type: AdType
}
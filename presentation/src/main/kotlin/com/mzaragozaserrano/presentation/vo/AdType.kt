package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R

sealed class AdType(@StringRes val textId: Int) {
    data object Rent : AdType(textId = R.string.type_rent)
    data object Sale : AdType(textId = R.string.type_sale)
}
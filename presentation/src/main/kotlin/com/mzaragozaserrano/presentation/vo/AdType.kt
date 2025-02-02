package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R
import java.io.Serializable

sealed class AdType(@StringRes val textId: Int) : Serializable {
    data object Rent : AdType(textId = R.string.type_rent)
    data object Sale : AdType(textId = R.string.type_sale)
}
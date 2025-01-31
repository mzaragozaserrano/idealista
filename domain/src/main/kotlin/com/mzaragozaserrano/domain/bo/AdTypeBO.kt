package com.mzaragozaserrano.domain.bo

import androidx.annotation.StringRes
import com.mzaragozaserrano.domain.R

sealed class AdTypeBO(@StringRes val textId: Int) {
    data object Flat : AdTypeBO(textId = R.string.type_flat)
}
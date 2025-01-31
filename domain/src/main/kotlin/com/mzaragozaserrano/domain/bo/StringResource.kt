package com.mzaragozaserrano.domain.bo

import androidx.annotation.StringRes
import com.mzaragozaserrano.domain.R

sealed class StringResource(@StringRes val textId: Int) {
    data object Exterior : StringResource(textId = R.string.exterior)
    data object Flat : StringResource(textId = R.string.type_flat)
    data object Interior : StringResource(textId = R.string.interior)
}
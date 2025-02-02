package com.mzaragozaserrano.presentation.utils

import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R

sealed class PresentationStringResource(@StringRes val textId: Int) {
    data object TitleDescription :
        PresentationStringResource(textId = R.string.title_description)
}
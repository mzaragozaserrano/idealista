package com.mzaragozaserrano.domain.utils

import androidx.annotation.StringRes
import com.mzaragozaserrano.domain.R
import java.io.Serializable

sealed class DomainStringResource(@StringRes val textId: Int): Serializable {
    data object Exterior : DomainStringResource(textId = R.string.exterior)
    data object Flat : DomainStringResource(textId = R.string.type_flat)
    data object Interior : DomainStringResource(textId = R.string.interior)
    data object No : DomainStringResource(textId = R.string.no)
    data object Yes : DomainStringResource(textId = R.string.yes)
}
package com.mzaragozaserrano.presentation.vo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R

sealed class Info(
    @DrawableRes val iconId: Int,
    @StringRes val labelId: Int,
    open val value: String,
) {
    data class Floor(override val value: String, val secondValue: String? = null) :
        Info(iconId = R.drawable.ic_floor, labelId = R.string.floor, value = value)

    data class Rooms(override val value: String) :
        Info(iconId = R.drawable.ic_rooms, labelId = R.string.rooms, value = value)

    data class SquareMeters(override val value: String) :
        Info(iconId = R.drawable.ic_square_meters, labelId = R.string.square_meters, value = value)

}
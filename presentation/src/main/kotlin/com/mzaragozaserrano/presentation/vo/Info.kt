package com.mzaragozaserrano.presentation.vo

import androidx.annotation.DrawableRes
import com.mzaragozaserrano.presentation.R
import java.io.Serializable

sealed class Info(
    @DrawableRes val iconId: Int,
    val labelId: Int,
    open val value: String = "",
) : Serializable {

    data class Floor(override val value: String, val secondValue: String? = null) :
        Info(iconId = R.drawable.ic_floor, labelId = R.string.floor, value = value)

    data class Rooms(override val value: String) :
        Info(iconId = R.drawable.ic_rooms, labelId = R.string.rooms, value = value)

    data class SquareMeters(override val value: String) :
        Info(iconId = R.drawable.ic_square_meters, labelId = R.string.square_meters, value = value)

}
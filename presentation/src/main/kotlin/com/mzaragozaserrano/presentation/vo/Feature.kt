package com.mzaragozaserrano.presentation.vo

import androidx.annotation.DrawableRes
import com.mzaragozaserrano.presentation.R
import java.io.Serializable

sealed class Feature(@DrawableRes val iconId: Int, val id: Int): Serializable {
    data object AirConditioning : Feature(iconId = R.drawable.ic_air_conditioning, id = 1)
    data object BoxRoom : Feature(iconId = R.drawable.ic_box_room, id = 2)
    data object Garden : Feature(iconId = R.drawable.ic_gargen, id = 3)
    data object Parking : Feature(iconId = R.drawable.ic_parking, id = 3)
    data object SwimmingPool : Feature(iconId = R.drawable.ic_swimming_pool, id = 4)
    data object Terrace : Feature(iconId = R.drawable.ic_terrace, id = 5)
}
package com.mzaragozaserrano.presentation.vo

import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.mzaragozaserrano.presentation.R

sealed class MoreInfo(@DrawableRes val iconId: Int, @StringRes val labelId: Int) {

    sealed class Building :
        MoreInfo(iconId = R.drawable.ic_floor, labelId = R.string.title_building) {
        data class Floor(
            val exterior: String,
            val floor: String,
            @StringRes val textId: Int = R.string.floor,
        ) : Building()

        data class Lift(val value: String) : Building()
    }

    sealed class EnergyCertification :
        MoreInfo(iconId = R.drawable.ic_energy, labelId = R.string.title_energy_certification) {
        data class Consume(val value: String, @StringRes val textId: Int = R.string.consume) :
            EnergyCertification()

        data class Emission(val value: String, @StringRes val textId: Int = R.string.emissions) :
            EnergyCertification()
    }

    sealed class Generic :
        MoreInfo(iconId = R.drawable.ic_square_meters, labelId = R.string.title_generic) {
        data class Bathroom(
            val value: Int,
            @PluralsRes val textId: Int = R.plurals.bathrooms,
        ) : Generic()

        data class ConstructedArea(
            val value: String,
            @StringRes val textId: Int = R.string.square_meters_expanded,
        ) : Generic()

        data class Rooms(
            val value: Int,
            @PluralsRes val textId: Int = R.plurals.rooms,
        ) : Generic()

        data class Status(val value: String): Generic()
    }

}
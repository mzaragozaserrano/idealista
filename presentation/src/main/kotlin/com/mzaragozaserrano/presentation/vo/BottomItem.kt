package com.mzaragozaserrano.presentation.vo

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.mzaragozaserrano.presentation.R

sealed class BottomItem(val icon: ImageVector, val id: Int, @StringRes val textId: Int) {
    data object Favorite :
        BottomItem(icon = Icons.Rounded.Favorite, id = 1, textId = R.string.bottom_item_favorite)

    data object Home :
        BottomItem(icon = Icons.Rounded.Home, id = 0, textId = R.string.bottom_item_home)
}

fun createBottomItemsList() = listOf(BottomItem.Home, BottomItem.Favorite)
package com.mzaragozaserrano.presentation.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mzaragozaserrano.presentation.composables.screens.FavoritesScreen
import com.mzaragozaserrano.presentation.composables.screens.HomeScreen
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzs.core.presentation.navigation.screenNavigation
import kotlinx.serialization.Serializable

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    adChanged: AdVO?,
    startDestination: Any,
    optionSelected: Filter?,
    onCardClicked: (AdVO) -> Unit,
) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        screenNavigation<Favorites> {
            FavoritesScreen(
                adChanged = adChanged,
                optionSelected = optionSelected,
                onCardClicked = onCardClicked
            )
        }
        screenNavigation<Home> {
            HomeScreen(
                adChanged = adChanged,
                optionSelected = optionSelected,
                onCardClicked = onCardClicked
            )
        }
    }

}

@Serializable
data object Favorites

@Serializable
data object Home
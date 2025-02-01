package com.mzaragozaserrano.presentation.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mzaragozaserrano.presentation.composables.screens.HomeScreen
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzs.core.presentation.navigation.screenNavigation
import kotlinx.serialization.Serializable

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    optionSelected: Filter?,
    onPagedChanged: (Int) -> Unit,
) {

    val navController = rememberNavController()

    NavHost(modifier = modifier, navController = navController, startDestination = Home) {
        screenNavigation<Favorites> {

        }
        screenNavigation<Home> {
            HomeScreen(optionSelected = optionSelected, onPageChanged = onPagedChanged)
        }
    }

}

@Serializable
data object Favorites

@Serializable
data object Home
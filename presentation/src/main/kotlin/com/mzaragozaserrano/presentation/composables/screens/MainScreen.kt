package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.mzaragozaserrano.presentation.composables.items.BottomNavigationBar
import com.mzaragozaserrano.presentation.composables.navigation.Navigation
import com.mzaragozaserrano.presentation.vo.BottomItem
import com.mzaragozaserrano.presentation.vo.createBottomItemsList

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = createBottomItemsList(),
                onItemSelected = { item ->
                    when (item) {
                        is BottomItem.Favorite -> {

                        }

                        is BottomItem.Home -> {

                        }
                    }
                }
            )
        },
        content = { contentPadding ->
            Navigation(modifier = Modifier.padding(paddingValues = contentPadding))
        }
    )

}
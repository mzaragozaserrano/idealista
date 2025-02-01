package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.composables.items.BottomNavigationBar
import com.mzaragozaserrano.presentation.composables.items.FilterButton
import com.mzaragozaserrano.presentation.composables.navigation.Navigation
import com.mzaragozaserrano.presentation.vo.BottomItem
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzaragozaserrano.presentation.vo.createBottomItemsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var optionSelected by remember { mutableStateOf<Filter?>(value = null) }
    var showTopBar by remember { mutableStateOf(value = true) }

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
        floatingActionButton = {
            FilterButton(
                filterSelected = null,
                onOptionClicked = { option -> optionSelected = option }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            /* AnimatedVisibility(
                 visible = showTopBar,
                 enter = slideInVertically(
                     initialOffsetY = { -it },
                     animationSpec = spring(
                         stiffness = Spring.StiffnessLow,
                         visibilityThreshold = IntOffset.VisibilityThreshold
                     )
                 ),
                 exit = slideOutVertically(
                     targetOffsetY = { -it }, animationSpec = spring(
                         stiffness = Spring.StiffnessLow,
                         visibilityThreshold = IntOffset.VisibilityThreshold
                     )
                 )
             ) {*/
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            )
//            }
        },
        content = { contentPadding ->
            Navigation(
                modifier = Modifier.padding(paddingValues = contentPadding),
                optionSelected = optionSelected,
                onPagedChanged = { showTopBar = it == 0 }
            )
        }
    )

}
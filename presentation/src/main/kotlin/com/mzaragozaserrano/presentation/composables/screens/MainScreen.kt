package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.mzaragozaserrano.presentation.composables.navigation.Favorites
import com.mzaragozaserrano.presentation.composables.navigation.Home
import com.mzaragozaserrano.presentation.composables.navigation.Navigation
import com.mzaragozaserrano.presentation.vo.BottomItem
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzaragozaserrano.presentation.vo.createBottomItemsList
import com.mzs.core.presentation.utils.generic.emptyText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onCardClicked: () -> Unit) {

    var destination by remember { mutableStateOf<Any>(value = Home) }
    var optionSelected by remember { mutableStateOf<Filter?>(value = null) }
    var showTopBar by remember { mutableStateOf(value = true) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = createBottomItemsList(),
                onItemSelected = { item ->
                    destination = when (item) {
                        is BottomItem.Favorite -> {
                            Favorites
                        }

                        is BottomItem.Home -> {
                            Home
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
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    AnimatedContent(
                        targetState = destination is Home,
                        transitionSpec = {
                            if (targetState) {
                                slideInVertically(initialOffsetY = { -it }) togetherWith slideOutVertically(
                                    targetOffsetY = { it })
                            } else {
                                slideInVertically(initialOffsetY = { it }) togetherWith slideOutVertically(
                                    targetOffsetY = { -it })
                            }
                        },
                        content = { isHome ->
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                text = stringResource(id = if (isHome) R.string.favorites_home else R.string.favorites_toolbar),
                                style = MaterialTheme.typography.headlineLarge
                            )
                        },
                        label = emptyText
                    )
                }
            )
        },
        content = { contentPadding ->
            Navigation(
                modifier = Modifier.padding(paddingValues = contentPadding),
                startDestination = destination,
                optionSelected = optionSelected,
                onCardClicked = onCardClicked,
                onPagedChanged = { showTopBar = it == 0 }
            )
        }
    )

}
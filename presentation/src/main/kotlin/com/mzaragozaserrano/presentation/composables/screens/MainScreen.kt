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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.composables.items.BottomNavigationBar
import com.mzaragozaserrano.presentation.composables.items.FilterButton
import com.mzaragozaserrano.presentation.composables.navigation.Home
import com.mzaragozaserrano.presentation.composables.navigation.Navigation
import com.mzaragozaserrano.presentation.viewmodels.MainViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.createBottomItemsList
import com.mzs.core.presentation.utils.generic.emptyText
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    adChanged: AdVO?,
    viewModel: MainViewModel = koinViewModel(),
    onCardClicked: (AdVO) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            uiState.success?.let { success ->
                BottomNavigationBar(
                    itemSelected = success.bottomItem,
                    items = createBottomItemsList(),
                    onItemSelected = { item ->
                        viewModel.onSetDestination(item = item)
                    }
                )
            }
        },
        floatingActionButton = {
            uiState.success?.let { success ->
                FilterButton(
                    filterSelected = success.optionSelected,
                    onOptionClicked = { option -> viewModel.onSetFilter(option = option) }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("HOLA"),
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    AnimatedContent(
                        targetState = uiState.success?.destination is Home,
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
            uiState.success?.let { success ->
                Navigation(
                    modifier = Modifier.padding(paddingValues = contentPadding),
                    adChanged = adChanged,
                    startDestination = success.destination,
                    optionSelected = success.optionSelected,
                    onCardClicked = onCardClicked
                )
            }
        }
    )

}
package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.composables.items.ErrorDialog
import com.mzaragozaserrano.presentation.composables.items.ProgressDialog
import com.mzaragozaserrano.presentation.viewmodels.HomeViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.Filter
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    adChanged: AdVO?,
    optionSelected: Filter?,
    viewModel: HomeViewModel = koinViewModel(),
    onCardClicked: (AdVO) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.onCreate(optionSelected = optionSelected)
        }
    )

    LaunchedEffect(
        key1 = adChanged,
        block = {
            if (adChanged != null) {
                viewModel.onExecuteGetAllAds(optionSelected = optionSelected)
            }
        }
    )

    LaunchedEffect(
        key1 = optionSelected,
        block = {
            if (optionSelected != uiState.success?.optionSelected && optionSelected != null) {
                viewModel.onRefreshList(optionSelected = optionSelected)
            }
        }
    )


    uiState.error?.let { error ->
        ErrorDialog(
            modifier = Modifier.padding(horizontal = 16.dp),
            buttonText = stringResource(id = R.string.button_retry),
            messageText = stringResource(id = error.messageId),
            onButtonClicked = {
                viewModel.onExecuteGetAllAds(optionSelected = optionSelected)
            }
        )
    }
    if (uiState.loading) {
        ProgressDialog()
    }
    uiState.success?.let { success ->
        AnimatedVerticalViewPager(
            modifier = modifier.fillMaxSize(),
            ads = success.currentAds,
            onCardClicked = onCardClicked,
            onFavoriteClicked = { id, isFavorite ->
                viewModel.onFavoriteClicked(id = id, isFavorite = isFavorite)
            }
        )
    }

}
package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.base.BaseViewModel
import com.mzaragozaserrano.presentation.composables.items.ErrorDialog
import com.mzaragozaserrano.presentation.composables.items.ProgressDialog
import com.mzaragozaserrano.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.onCreate()
    }

    uiState.error?.let { error ->
        ErrorDialog(
            buttonText = stringResource(id = R.string.retry_button),
            messageText = stringResource(id = error.messageId),
            onButtonClicked = {
                viewModel.onExecuteGetAllAds()
            }
        )
    }
    if(uiState.loading) {
        ProgressDialog()
    }
    uiState.success?.let { ads ->
        ListScreen(
            modifier = modifier,
            ads = ads,
            onCardClicked = { },
            onFavoriteClicked = { id, isFavorite ->
                viewModel.onFavoriteClicked(id = id, isFavorite = isFavorite)
            }
        )
    }

}
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
import com.mzaragozaserrano.presentation.composables.items.FavoritesAdsList
import com.mzaragozaserrano.presentation.composables.items.ProgressDialog
import com.mzaragozaserrano.presentation.viewmodels.FavoritesViewModel
import com.mzaragozaserrano.presentation.vo.Filter
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    optionSelected: Filter?,
    viewModel: FavoritesViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.onCreate(optionSelected = optionSelected)
    }

    LaunchedEffect(key1 = optionSelected) {
        if (optionSelected != uiState.success?.optionSelected && optionSelected != null) {
            viewModel.onRefreshList(optionSelected = optionSelected)
        }
    }

    uiState.error?.let { error ->
        ErrorDialog(
            buttonText = stringResource(id = R.string.retry_button),
            messageText = stringResource(id = error.messageId),
            onButtonClicked = {
                viewModel.onExecuteGetAllFavorites(optionSelected = optionSelected)
            }
        )
    }
    if (uiState.loading) {
        ProgressDialog()
    }
    uiState.success?.let { success ->
        FavoritesAdsList(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            ads = success.currentAds
        )
    }

}
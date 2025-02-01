package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.composables.items.ErrorDialog
import com.mzaragozaserrano.presentation.composables.items.ProgressDialog
import com.mzaragozaserrano.presentation.viewmodels.HomeViewModel
import com.mzaragozaserrano.presentation.vo.Filter
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    optionSelected: Filter?,
    viewModel: HomeViewModel = koinViewModel(),
    onPageChanged: (Int) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.onCreate()
    }

    LaunchedEffect(key1 = optionSelected) {
        if(optionSelected != uiState.success?.optionSelected && optionSelected != null) {
            viewModel.onRefreshList(optionSelected = optionSelected)
        }
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
    if (uiState.loading) {
        ProgressDialog()
    }
    uiState.success?.let { success ->
        ListScreen(
            modifier = modifier,
            ads = success.currentAds,
            onCardClicked = { },
            onFavoriteClicked = { id, isFavorite ->
                viewModel.onFavoriteClicked(id = id, isFavorite = isFavorite)
            },
            onPageChanged = onPageChanged
        )
    }

}
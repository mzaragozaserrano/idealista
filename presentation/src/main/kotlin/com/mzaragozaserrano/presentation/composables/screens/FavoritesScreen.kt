package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.composables.items.ErrorDialog
import com.mzaragozaserrano.presentation.composables.items.FavoritesAdsList
import com.mzaragozaserrano.presentation.composables.items.ProgressDialog
import com.mzaragozaserrano.presentation.viewmodels.FavoritesViewModel
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzs.core.presentation.components.compose.images.LottieImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    adChanged: AdVO?,
    optionSelected: Filter?,
    onCardClicked: (AdVO) -> Unit,
    viewModel: FavoritesViewModel = koinViewModel(),
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
                viewModel.onExecuteGetAllFavorites(optionSelected = optionSelected)
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
            buttonText = stringResource(id = R.string.button_retry),
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
        if (success.currentAds.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                ),
                content = {
                    LottieImage(animationId = R.raw.empty_favorites)
                    Text(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                        text = stringResource(id = R.string.not_favorites_title),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(id = R.string.not_favorites_subtitle),
                        textAlign = TextAlign.Center
                    )
                }
            )
        } else {
            FavoritesAdsList(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                ads = success.currentAds,
                onCardClicked = onCardClicked
            )
        }
    }

}
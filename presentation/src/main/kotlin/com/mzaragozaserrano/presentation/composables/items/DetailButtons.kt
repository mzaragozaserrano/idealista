package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.R
import com.mzs.core.presentation.components.compose.buttons.PushedButton

@Composable
fun DetailButtons(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteButtonClicked: (Boolean) -> Unit,
    onMapButtonClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 24.dp)
    ) {
        PushedButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f),
            buttonBackgroundColor = MaterialTheme.colorScheme.primary,
            text = stringResource(id = R.string.button_map),
            textColor = MaterialTheme.colorScheme.onPrimary,
            textStyle = MaterialTheme.typography.bodyLarge,
            onButtonClicked = onMapButtonClicked
        )
        PushedButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f),
            buttonBackgroundColor = MaterialTheme.colorScheme.primary,
            text = stringResource(
                id = if (isFavorite) {
                    R.string.button_saved
                } else {
                    R.string.button_not_saved
                }
            ),
            textColor = MaterialTheme.colorScheme.onPrimary,
            textStyle = MaterialTheme.typography.bodyLarge,
            onButtonClicked = {
                onFavoriteButtonClicked(isFavorite)
            }
        )
    }
}
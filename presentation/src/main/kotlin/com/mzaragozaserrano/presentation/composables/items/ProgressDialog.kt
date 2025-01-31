package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.R
import com.mzs.core.presentation.components.compose.images.LottieImage

@Composable
fun ProgressDialog() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieImage(
            modifier = Modifier.padding(all = 128.dp),
            animationId = R.raw.loading
        )
    }
}

@PreviewLightDark
@Composable
private fun ProgressDialogPrev() {
    ProgressDialog()
}
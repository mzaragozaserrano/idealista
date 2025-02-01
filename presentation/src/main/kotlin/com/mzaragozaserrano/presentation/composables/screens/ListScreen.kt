package com.mzaragozaserrano.presentation.composables.screens

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.composables.items.AddItem
import com.mzaragozaserrano.presentation.vo.AdVO

@Composable
fun ListScreen(
    modifier: Modifier,
    cornerRadius: Dp = 20.dp,
    ads: List<AdVO>,
    onCardClicked: () -> Unit,
    onFavoriteClicked: (String, Boolean) -> Unit,
    onPageChanged: (Int) -> Unit,
) {

    val state = rememberPagerState(pageCount = { ads.size })

    LaunchedEffect(state.currentPage) {
        onPageChanged(state.currentPage)
    }

    VerticalPager(
        modifier = modifier,
        beyondViewportPageCount = 1,
        state = state,
        pageSpacing = 16.dp,
        pageContent = { page ->
            AddItem(
                page = page,
                state = state,
                cornerRadius = cornerRadius,
                onCardClicked = onCardClicked,
                ads = ads,
                modifier = modifier,
                onFavoriteClicked = onFavoriteClicked,
            )
        }
    )
}
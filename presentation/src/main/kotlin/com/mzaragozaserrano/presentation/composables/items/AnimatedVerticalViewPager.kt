package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.vo.AdVO

@Composable
fun AnimatedVerticalViewPager(
    modifier: Modifier,
    ads: List<AdVO>,
    onCardClicked: (AdVO) -> Unit,
    onFavoriteClicked: (String, Boolean) -> Unit,
) {

    val state = rememberPagerState(pageCount = { ads.size })

    VerticalPager(
        modifier = modifier,
        beyondViewportPageCount = 1,
        key = { ads[it].id },
        state = state,
        pageSpacing = 16.dp,
        pageContent = { page ->
            HomeAdCard(
                page = page,
                state = state,
                onCardClicked = onCardClicked,
                ad = ads[page],
                modifier = modifier,
                onFavoriteClicked = onFavoriteClicked,
            )
        }
    )
}
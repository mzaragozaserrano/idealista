package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.R
import com.mzs.core.presentation.components.compose.images.UrlImage
import com.mzs.core.presentation.utils.generic.emptyText

@Composable
fun MultimediaCarousel(modifier: Modifier = Modifier, images: List<String>) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    Box(
        modifier = modifier.fillMaxSize(),
        content = {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                beyondViewportPageCount = 1,
                state = pagerState,
                pageContent = { page ->
                    UrlImage(
                        animationId = R.raw.image_loading,
                        contentScale = ContentScale.Crop,
                        url = images[page]
                    )
                }
            )
            DotsIndicator(images = images, pagerState = pagerState)
        }
    )
}

@Composable
fun BoxScope.DotsIndicator(images: List<String>, pagerState: PagerState) {
    val indicatorScrollState = rememberLazyListState()
    LaunchedEffect(
        key1 = pagerState.currentPage,
        block = {
            val currentPage = pagerState.currentPage
            val size = indicatorScrollState.layoutInfo.visibleItemsInfo.size
            val lastVisibleIndex =
                indicatorScrollState.layoutInfo.visibleItemsInfo.last().index
            val firstVisibleItemIndex = indicatorScrollState.firstVisibleItemIndex

            if (currentPage > lastVisibleIndex - 1) {
                indicatorScrollState.animateScrollToItem(currentPage - size + 2)
            } else if (currentPage <= firstVisibleItemIndex + 1) {
                indicatorScrollState.animateScrollToItem((currentPage - 1).coerceAtLeast(0))
            }
        }
    )
    LazyRow(
        modifier = Modifier
            .height(50.dp)
            .align(Alignment.BottomCenter),
        state = indicatorScrollState,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            repeat(images.size) { iteration ->
                item(key = "item$iteration") {
                    val color =
                        if (pagerState.currentPage == iteration) {
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        }
                    val currentPage = pagerState.currentPage
                    val firstVisibleIndex by remember { derivedStateOf { indicatorScrollState.firstVisibleItemIndex } }
                    val lastVisibleIndex =
                        indicatorScrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                            ?: 0
                    val size by animateDpAsState(
                        targetValue = when (iteration) {
                            currentPage -> {
                                10.dp
                            }

                            in firstVisibleIndex + 1..<lastVisibleIndex -> {
                                10.dp
                            }

                            else -> {
                                6.dp
                            }
                        },
                        label = emptyText
                    )
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color = color, shape = CircleShape)
                            .size(size = size)
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun MultimediaCarouselPrev() {
    MultimediaCarousel(
        images = listOf(
            "https://img4.idealista.com/blur/WEB_DETAIL-L-L/0/id.pro.es.image.master/6b/39/a4/1273036799.webp",
            "https://img4.idealista.com/blur/WEB_DETAIL-L-L/0/id.pro.es.image.master/f3/d4/df/1273036798.webp"
        )
    )
}
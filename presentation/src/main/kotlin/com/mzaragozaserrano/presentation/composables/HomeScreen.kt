package com.mzaragozaserrano.presentation.composables

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzs.core.presentation.components.compose.images.ResourceImage
import com.mzs.core.presentation.components.compose.utils.Adapter
import com.mzs.core.presentation.utils.generic.ItemOrientation

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    data: List<AdVO>,
    cornerRadius: Dp = 20.dp,
    onCardClicked: () -> Unit,
) {
    val horizontalState = rememberPagerState(pageCount = { data.size })
    VerticalPager(
        modifier = modifier.padding(vertical = 32.dp),
        beyondViewportPageCount = 1,
        state = horizontalState,
        pageSpacing = 16.dp,
        pageContent = { page ->
            Card(
                modifier = Modifier
                    .zIndex(zIndex = page * 10f)
                    .padding(start = 32.dp, end = 32.dp, top = 128.dp, bottom = 64.dp)
                    .graphicsLayer {
                        val startOffset =
                            ((horizontalState.currentPage - page) + horizontalState.currentPageOffsetFraction).coerceAtLeast(
                                minimumValue = 0f
                            )
                        translationY = size.height * (startOffset * 0.99f)
                        alpha = (2f - startOffset) / 2f
                        val blur = (startOffset * 20f).coerceAtLeast(minimumValue = 0.1f)
                        renderEffect = RenderEffect
                            .createBlurEffect(blur, blur, Shader.TileMode.DECAL)
                            .asComposeRenderEffect()
                        val scale = 1f - (startOffset * 0.1f)
                        scaleX = scale
                        scaleY = scale
                    }
                    .clip(shape = RoundedCornerShape(size = cornerRadius))
                    .clickable { onCardClicked() },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                content = {
                    Column {
                        AsyncImage(
                            modifier = Modifier
                                .weight(weight = 1f)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = cornerRadius,
                                        topEnd = cornerRadius
                                    )
                                ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(data = data[page].thumbnail)
                                .memoryCachePolicy(policy = CachePolicy.ENABLED)
                                .build()
                        )
                        Column(
                            modifier = Modifier.padding(all = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                            content = {
                                Adapter(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    arrangement = Arrangement.spacedBy(space = 4.dp),
                                    contentPadding = 0.dp,
                                    isScrollable = false,
                                    itemOrientation = ItemOrientation.Horizontal,
                                    items = data[page].features,
                                    item = { _, item ->
                                        ResourceImage(
                                            iconId = item.iconId,
                                            iconTint = MaterialTheme.colorScheme.inversePrimary,
                                            size = 16.dp
                                        )
                                    },
                                    key = { _, item -> item.id }
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 18.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = data[page].title
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 18.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = data[page].subtitle
                                )
                                Text(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 24.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = data[page].price
                                )
                            }
                        )
                    }
                }
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun HomeScreenPrev() {
    HomeScreen(data = listOf()) { }
}
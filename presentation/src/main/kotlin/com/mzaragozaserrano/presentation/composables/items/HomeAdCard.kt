package com.mzaragozaserrano.presentation.composables.items

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.vo.AdVO
import com.mzaragozaserrano.presentation.vo.Info
import com.mzs.core.presentation.components.compose.images.ResourceImage
import com.mzs.core.presentation.components.compose.images.UrlImage
import com.mzs.core.presentation.components.compose.labels.WavyLabel
import com.mzs.core.presentation.components.compose.utils.Adapter
import com.mzs.core.presentation.utils.generic.ItemOrientation
import com.mzs.core.presentation.utils.generic.emptyText

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun HomeAdCard(
    modifier: Modifier,
    ad: AdVO,
    page: Int,
    state: PagerState,
    onCardClicked: (AdVO) -> Unit,
    onFavoriteClicked: (String, Boolean) -> Unit,
) {

    var isFavorite by remember(ad.id) { mutableStateOf(value = ad.isFavorite) }
    var isPressed by remember { mutableStateOf(value = false) }

    val startOffset =
        ((state.currentPage - page) + state.currentPageOffsetFraction).coerceAtLeast(
            minimumValue = 0f
        )

    val scaleAnimation by animateFloatAsState(
        animationSpec = tween(durationMillis = 250, easing = EaseInOut),
        finishedListener = {
            isPressed = false
        },
        targetValue = if (isPressed && isFavorite) {
            1.1f
        } else {
            1f
        },
        label = emptyText
    )

    val sizeAnimation by animateDpAsState(
        animationSpec = tween(durationMillis = 250, easing = EaseInOut),
        finishedListener = {
            isPressed = false
        },
        targetValue = if (isPressed && isFavorite) {
            40.dp
        } else {
            32.dp
        },
        label = emptyText
    )

    val shakeAnimation = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(
        key1 = isFavorite,
        block = {
            if (isPressed && isFavorite.not()) {
                repeat(times = 6) {
                    shakeAnimation.animateTo(
                        animationSpec = tween(durationMillis = 50, easing = LinearEasing),
                        targetValue = if (it % 2 == 0) 10f else -10f
                    )
                }
                shakeAnimation.animateTo(targetValue = 0f)
                if (shakeAnimation.isRunning.not()) {
                    isPressed = false
                }
            }
        }
    )

    Card(
        modifier = Modifier
            .zIndex(zIndex = page * 10f)
            .padding(start = 32.dp, end = 32.dp, top = 128.dp, bottom = 64.dp)
            .graphicsLayer {
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
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .offset(x = shakeAnimation.value.dp, y = 0.dp)
            .clickable { onCardClicked(ad) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        content = {
            UrlImage(
                modifier = Modifier.weight(weight = 1f),
                animationId = R.raw.image_loading,
                contentScale = ContentScale.Crop,
                url = ad.thumbnail
            )
            LazyColumn(
                modifier = Modifier.padding(all = 16.dp),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                content = {
                    if (ad.features.isNotEmpty()) {
                        item {
                            Adapter(
                                modifier = Modifier.padding(vertical = 2.dp),
                                arrangement = Arrangement.spacedBy(space = 8.dp),
                                contentPadding = 0.dp,
                                isScrollable = false,
                                itemOrientation = ItemOrientation.Horizontal,
                                items = ad.features,
                                item = { _, item ->
                                    ResourceImage(
                                        iconId = item.iconId,
                                        iconTint = MaterialTheme.colorScheme.inversePrimary,
                                        size = 16.dp
                                    )
                                },
                                key = { _, item -> item.id }
                            )
                        }
                    }
                    ad.prefixTitle?.let { prefix ->
                        if (ad.title.isNotEmpty()) {
                            item {
                                Text(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    text = stringResource(id = prefix.textId, ad.title),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                    if (ad.title.isNotEmpty()) {
                        item {
                            Text(
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = ad.subtitle,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    if (ad.price.isNotEmpty()) {
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 2.dp),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = ad.price + ad.currencySuffix,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 16.dp,
                                alignment = CenterHorizontally
                            ),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            if (ad.extraInfo.isNotEmpty()) {
                                FlowRow(
                                    modifier = modifier.weight(weight = 1f),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 8.dp,
                                        alignment = Alignment.Start
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 8.dp,
                                        alignment = Alignment.CenterVertically
                                    ),
                                    content = {
                                        ad.extraInfo.forEach { item ->
                                            WavyLabel(
                                                buttonBackgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                                                iconBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                                iconId = item.iconId,
                                                iconTint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                text = if (item is Info.Floor && item.secondValue != null) {
                                                    stringResource(
                                                        id = item.labelId,
                                                        item.value,
                                                        item.secondValue
                                                    )
                                                } else {
                                                    stringResource(
                                                        id = item.labelId,
                                                        item.value
                                                    )
                                                },
                                                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                                textStyle = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    }
                                )
                            }
                            if (ad.id.isNotEmpty()) {
                                Crossfade(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    targetState = isFavorite,
                                    content = { favoriteState ->
                                        Icon(
                                            modifier = Modifier
                                                .size(size = if (isPressed) sizeAnimation else 32.dp)
                                                .clip(shape = CircleShape)
                                                .clickable {
                                                    onFavoriteClicked(ad.id, isFavorite)
                                                    isFavorite = isFavorite.not()
                                                    isPressed = isPressed.not()
                                                }
                                                .graphicsLayer(
                                                    scaleX = if (isPressed) scaleAnimation else 1f,
                                                    scaleY = if (isPressed) scaleAnimation else 1f
                                                ),
                                            imageVector = if (favoriteState) {
                                                Icons.Rounded.Favorite
                                            } else {
                                                Icons.Rounded.FavoriteBorder
                                            },
                                            tint = MaterialTheme.colorScheme.inversePrimary,
                                            contentDescription = emptyText
                                        )
                                    },
                                    label = emptyText
                                )
                            }
                        }
                    }
                    if (ad.hasNotInformation) {
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 2.dp),
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                text = stringResource(id = R.string.not_info_available),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                }
            )
        }
    )
}
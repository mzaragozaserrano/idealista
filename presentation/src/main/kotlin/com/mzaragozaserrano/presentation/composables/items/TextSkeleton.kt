package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mzs.core.presentation.components.compose.utils.Skeletonable
import com.mzs.core.presentation.components.compose.utils.SkeletonableItem
import com.mzs.core.presentation.utils.extensions.conditional
import com.mzs.core.presentation.utils.functions.rememberShimmerBrush

@JvmName("TextSkeletonString")
@Composable
fun TextSkeleton(
    modifier: Modifier = Modifier,
    height: Dp = 12.dp,
    maxLines: Int = 1,
    skeletonable: Skeletonable<String>,
    textColor: Color,
    textStyle: TextStyle,
    width: Dp? = null,
) {
    val shimmerBrush = rememberShimmerBrush(color = MaterialTheme.colorScheme.outline)
    SkeletonableItem(
        skeletonable = skeletonable,
        contentSkeleton = {
            Column(verticalArrangement = Arrangement.spacedBy(space = 4.dp)) {
                repeat(maxLines) {
                    Box(
                        modifier = modifier
                            .background(brush = shimmerBrush)
                            .conditional(
                                condition = width,
                                modifier = { widthAux -> size(width = widthAux, height = height) },
                                modifierElse = { fillMaxWidth().height(height) }
                            )
                    )
                }
            }
        },
        content = { text ->
            if (text.isNotEmpty()) {
                Text(
                    color = textColor,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    text = text,
                    style = textStyle
                )
            }
        }
    )
}

@JvmName("TextSkeletonResource")
@Composable
fun TextSkeleton(
    modifier: Modifier = Modifier,
    height: Dp = 12.dp,
    maxLines: Int = 1,
    skeletonable: Skeletonable<Int>,
    textColor: Color,
    textStyle: TextStyle,
    width: Dp? = null,
) {
    val shimmerBrush = rememberShimmerBrush(color = MaterialTheme.colorScheme.outline)
    SkeletonableItem(
        skeletonable = skeletonable,
        contentSkeleton = {
            Box(
                modifier = modifier
                    .background(brush = shimmerBrush)
                    .conditional(
                        condition = width,
                        modifier = { widthAux -> size(width = widthAux, height = height) },
                        modifierElse = { fillMaxWidth().height(height) }
                    )
            )
        },
        content = { textId ->
            Text(
                color = textColor,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                text = stringResource(id = textId),
                style = textStyle
            )
        }
    )
}
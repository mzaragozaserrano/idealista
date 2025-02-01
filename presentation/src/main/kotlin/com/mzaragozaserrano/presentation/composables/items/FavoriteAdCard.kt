package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.mzaragozaserrano.presentation.R
import com.mzaragozaserrano.presentation.vo.AdType
import com.mzaragozaserrano.presentation.vo.FavoriteAdVO
import com.mzs.core.presentation.utils.generic.emptyText

@Composable
fun FavoriteAdCard(ad: FavoriteAdVO) {

    var isPressed by remember { mutableStateOf(value = false) }

    Card(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .clickable { isPressed = isPressed.not() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        content = {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.Top,
                content = {
                    AsyncImage(
                        modifier = Modifier.aspectRatio(ratio = 1f),
                        contentScale = ContentScale.Crop,
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(data = ad.thumbnail)
                            .transformations(RoundedCornersTransformation(radius = 20f))
                            .memoryCachePolicy(policy = CachePolicy.ENABLED)
                            .build(),
                        contentDescription = emptyText
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Top
                        ),
                        content = {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Column {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            text = ad.title,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            text = ad.subtitle,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            text = ad.price,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                    if (ad.date != null) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            color = MaterialTheme.colorScheme.inversePrimary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            text = stringResource(
                                                id = R.string.favorite_ad_saved,
                                                ad.date.split(" ")[1]
                                            ),
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )

}

@PreviewLightDark
@Composable
private fun FavoriteAdCardPrev() {
    FavoriteAdCard(
        ad = FavoriteAdVO(
            id = "1",
            date = "01/01/2001 00:00",
            price = "1.100.000",
            thumbnail = "https://via.placeholder.com/150",
            title = "Sor Ángela de la Cruz",
            subtitle = "Madrid",
            type = AdType.Rent
        )
    )
}
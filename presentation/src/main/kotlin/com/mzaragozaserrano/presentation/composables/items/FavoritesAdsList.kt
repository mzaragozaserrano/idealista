package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.vo.FavoriteAdVO
import com.mzs.core.presentation.components.compose.utils.Adapter
import com.mzs.core.presentation.utils.generic.ItemOrientation

@Composable
fun FavoritesAdsList(
    modifier: Modifier = Modifier,
    ads: Map<String, List<FavoriteAdVO>>,
    onCardClicked: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        content = {
            ads.forEach { (date, adsForDay) ->
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = date,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                item {
                    Adapter(
                        modifier = Modifier.padding(bottom = 4.dp),
                        arrangement = Arrangement.spacedBy(space = 16.dp),
                        contentPadding = 16.dp,
                        itemOrientation = ItemOrientation.Horizontal,
                        items = adsForDay,
                        item = { _, ad ->
                            FavoriteAdCard(ad = ad, onCardClicked = onCardClicked)
                        }
                    )
                }
            }
        }
    )
}
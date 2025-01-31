package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.mzaragozaserrano.presentation.vo.BottomItem
import com.mzs.core.presentation.utils.generic.emptyText

@Composable
fun BottomNavigationBar(items: List<BottomItem>, onItemSelected: (BottomItem) -> Unit) {

    var screenSelected by remember { mutableIntStateOf(0) }

    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                    indicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    selectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                ),
                icon = {
                    Icon(imageVector = item.icon, contentDescription = emptyText)
                },
                label = {
                    Text(
                        text = stringResource(id = item.textId),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = item.id == screenSelected,
                onClick = {
                    screenSelected = item.id
                    onItemSelected(item)
                }
            )
        }

    }

}

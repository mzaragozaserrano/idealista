package com.mzaragozaserrano.presentation.composables.items

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.mzaragozaserrano.presentation.vo.Filter
import com.mzs.core.presentation.components.compose.utils.Line
import com.mzs.core.presentation.utils.generic.ItemOrientation
import com.mzs.core.presentation.utils.generic.emptyText

@Composable
fun FilterButton(filterSelected: Filter? = null, onOptionClicked: (Filter) -> Unit) {
    val list = listOf(Filter.All, Filter.Rent(), Filter.Sale())
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) {
            90f
        } else {
            0f
        },
        label = emptyText
    )
    var optionSelected by remember { mutableStateOf(filterSelected ?: list.first()) }

    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DropdownMenu(
                modifier = Modifier.width(width = 256.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                expanded = expanded,
                offset = DpOffset(x = 96.dp, y = 64.dp),
                onDismissRequest = { expanded = false },
                content = {
                    list.forEach { item ->
                        DropdownMenuItem(
                            leadingIcon = {
                                if (optionSelected == item) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = emptyText
                                    )
                                }
                            },
                            onClick = {
                                optionSelected = item
                                onOptionClicked(item)
                                expanded = false
                            },
                            text = {
                                Text(
                                    text = stringResource(id = item.textId),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        )
                        if (list.last() != item) {
                            Line(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.outline,
                                itemOrientation = ItemOrientation.Vertical
                            )
                        }
                    }
                }
            )
            FloatingActionButton(
                modifier = Modifier
                    .offset(y = 84.dp)
                    .rotate(degrees = rotation),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                shape = CircleShape,
                onClick = {
                    expanded = expanded.not()
                },
                content = {
                    Icon(
                        Icons.AutoMirrored.Rounded.List,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            )
        }
    }
}
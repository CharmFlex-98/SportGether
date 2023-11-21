package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> ListTable(
    modifier: Modifier = Modifier,
    items: List<T>,
    shownItemMaxCount: Int = -1,
    alignment: ListTableContentAlignment = ListTableContentAlignment.VERTICAL,
    itemContent: @Composable (Int, T) -> Unit,
) {
    var maxSize by remember { mutableStateOf(0.dp) }
    val itemSize = maxSize / shownItemMaxCount
    val localDensity = LocalDensity.current

    Box(modifier = modifier
        .onGloballyPositioned {
            maxSize = when (alignment) {
                ListTableContentAlignment.HORIZONTAL -> it.size.width
                else -> it.size.height
            }.let {
                with(localDensity) { it.toDp() }
            }
        }
    ) {
        when (alignment) {
            ListTableContentAlignment.HORIZONTAL -> {
                LazyRow {
                    items(alignment, items = items, itemSize = itemSize) { index, item ->
                        itemContent(index, item)
                    }
                }
            }

            else -> {
                LazyColumn {
                    items(alignment, items = items, itemSize = itemSize) { index, item ->
                        itemContent(index, item)
                    }
                }
            }
        }

    }
}
fun <T> LazyListScope.items(alignment: ListTableContentAlignment, items: List<T>, itemSize: Dp, itemContent: @Composable (Int, T) -> Unit) {
    this.itemsIndexed(items = items, itemContent = { index, item ->
        if (itemSize > 0.dp) {
            Box(
                modifier = if (alignment == ListTableContentAlignment.VERTICAL)
                    Modifier.height(itemSize)
                else
                    Modifier.width(itemSize)
            ) {
                itemContent(index, item)
            }
        } else {
            itemContent(index, item)
        }
    })
}


enum class ListTableContentAlignment {
    VERTICAL, HORIZONTAL
}
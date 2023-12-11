package com.charmflex.sportgether.sdk.ui_common

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun <T> ListTable(
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    items: List<T>,
    shownItemMaxCount: Int = -1,
    alignment: ListTableContentAlignment = ListTableContentAlignment.VERTICAL,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    batchSize: Int = 10,
    onBottomReach: () -> Unit = {},
    isFetchingNext: Boolean = false,
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
                LazyRow(contentPadding = contentPadding) {
                    items(alignment, items = items, itemSize = itemSize) { index, item ->
                        itemContent(index, item)
                    }
                }
            }

            else -> {
                val reachBottom by remember(items.size) {
                    derivedStateOf {
                        val lastIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
                        Log.d("HTTP::", "${lastIndex}, ${items.size - 1}, $items")
                        lastIndex >= items.size - 1
                    }
                }
                LazyColumn(contentPadding = contentPadding, state = scrollState) {
                    items(alignment, items = items, itemSize = itemSize) { index, item ->
                        itemContent(index, item)
                    }
                    if (isFetchingNext) item {
                        Box(
                            modifier = Modifier.padding(grid_x1).fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                LaunchedEffect(reachBottom) {
                    Log.d("HTTP::", "reach bottom = $reachBottom")
                    if (reachBottom) onBottomReach()
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

fun LazyListState.reachBottom(): Boolean {
    Log.d("HTTP::", "${layoutInfo.visibleItemsInfo.lastOrNull()?.index}  ${layoutInfo.totalItemsCount}")
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}


@Stable
enum class ListTableContentAlignment {
    VERTICAL, HORIZONTAL
}
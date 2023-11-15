package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> ListTable(
    modifier: Modifier,
    items: List<T>,
    itemContent: @Composable (Int, T) -> Unit
) {
    Box(modifier = modifier) {
        LazyColumn {
            itemsIndexed(items = items, itemContent = { index, item ->
                itemContent(index, item)
            })
        }
    }
}
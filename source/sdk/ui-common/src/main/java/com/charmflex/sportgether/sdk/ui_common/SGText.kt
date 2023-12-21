package com.charmflex.sportgether.sdk.ui_common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

@Composable
fun SGBasicTwoLineItem(
    modifier: Modifier,
    title: String,
    content: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(grid_x1)
    ) {
        Text(text = title, color = MaterialTheme.colorScheme.tertiary, fontSize = 10.sp)
        Text(text = content, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
    }
}

@Composable
fun SGBasicTwoLineIconsItem(
    modifier: Modifier = Modifier,
    iconSize: Dp,
    title: String,
    @DrawableRes
    icons: List<Int>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(grid_x1)
    ) {
        Text(text = title, color = MaterialTheme.colorScheme.tertiary, fontSize = 10.sp)
        Row {
            for (i in icons) SGRoundImage(filePath = i, modifier = Modifier.size(iconSize))
        }
    }
}


@Preview
@Composable
fun Preview1() {
    SportGetherTheme {
        Column {
            SGBasicTwoLineItem(modifier = Modifier.padding(horizontal = grid_x0_25), title = "Title", content = "My content")
            SGBasicTwoLineItem(modifier = Modifier.padding(horizontal = grid_x0_25), title = "Title", content = "My content")
        }
    }
}
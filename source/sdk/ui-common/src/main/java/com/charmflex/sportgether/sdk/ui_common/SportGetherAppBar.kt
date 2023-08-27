package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopBar(
    title: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    leadingIconAction: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (leadingIconAction != null && leadingIcon != null) {
                IconButton(onClick = leadingIconAction, content = leadingIcon)
            }
        }
    )
}

@Composable
@Preview
fun BasicTopBarPreview() {
    SportGetherTheme {
        BasicTopBar(title = "Testing")
    }
}
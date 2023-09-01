package com.charmflex.sportgether.app.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    SportGetherScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("This is home page")
        }
    }
}
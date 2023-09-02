package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SGTextField(
    modifier: Modifier,
    label: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(modifier = modifier.padding(bottom = grid_x2),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = hint) })
}
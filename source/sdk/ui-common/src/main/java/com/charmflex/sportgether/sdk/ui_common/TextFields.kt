package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SGTextField(
    modifier: Modifier,
    label: String,
    hint: String,
    value: String,
    errorText: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    enable: Boolean = true,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.padding(bottom = grid_x2),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = hint) },
        supportingText = errorText?.let {
            {
                Text(
                    text = errorText,
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        readOnly = readOnly,
        enabled = enable
    )
}
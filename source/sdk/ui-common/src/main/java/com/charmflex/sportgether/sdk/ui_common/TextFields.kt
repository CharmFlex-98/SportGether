package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.google.android.material.textfield.MaterialAutoCompleteTextView

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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
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
        enabled = enable,
        interactionSource = interactionSource
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SGAutoCompleteTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    label: String,
    value: String,
    hint: String,
    onDismiss: () -> Unit,
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    suggestions: List<String>,
    onValueChange: (String) -> Unit,
    onItemSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpandedChanged
    ) {
        SGTextField(
            modifier = Modifier.menuAnchor(),
            label = label,
            hint = hint,
            value = value,
            errorText = null,
            readOnly = false,
            enable = true,
            onValueChange = onValueChange
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
            suggestions.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { onItemSelected(it) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
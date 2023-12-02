package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SGDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    positiveText: String,
    negativeText: String? = null,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = { Text(text = text) },
        dismissButton = {
            if (negativeText != null) SGLargeSecondaryButton(
                modifier = Modifier.fillMaxSize(),
                text = negativeText,
                onClick = onDismissRequest
            )
        },
        confirmButton = {
            SGLargePrimaryButton(text = positiveText) {
                onConfirm()
            }
        }
    )
}
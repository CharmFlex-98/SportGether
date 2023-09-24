package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun SGSnackBar(
    snackBarHostState: SnackbarHostState,
    snackBarType: SnackBarType
) {
    SnackbarHost(hostState = snackBarHostState) {
        Snackbar(
            snackbarData = it,
            containerColor = SnackBarType.containerColor(snackBarType = snackBarType)
        )
    }
}

sealed interface SnackBarType {
    object Success : SnackBarType
    object Error : SnackBarType

    companion object {
        @Composable
        fun containerColor(snackBarType: SnackBarType): Color {
            return when (snackBarType) {
                Success -> MaterialTheme.colorScheme.primaryContainer
                Error -> MaterialTheme.colorScheme.errorContainer
            }
        }
    }
}

suspend fun SnackbarHostState.showSnackBarImmediately(message: String) {
    currentSnackbarData?.dismiss()
    showSnackbar(message = message, duration = SnackbarDuration.Short)
    delay(500)
}
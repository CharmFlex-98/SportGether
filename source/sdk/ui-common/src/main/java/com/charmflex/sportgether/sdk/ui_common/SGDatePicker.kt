package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SGDatePicker(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    date: Long?,
    isVisible: Boolean
) {
    datePickerState.selectedDateMillis = date

    val dismiss = remember {
        {
            datePickerState.selectedDateMillis = date
            onDismiss()
        }
    }

    if (isVisible) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = { CalendarConfirmButton(onClick = onConfirm) },
            dismissButton = { CalendarDismissButton(onClick = dismiss) }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun CalendarConfirmButton(onClick: () -> Unit) {
    SGMediumPrimaryButton(
        text = stringResource(id = R.string.calendar_confirm_button_text),
        onClick = onClick
    )
}

@Composable
private fun CalendarDismissButton(onClick: () -> Unit) {
    SGMediumPrimaryButton(text = stringResource(id = R.string.calendar_dismiss_button_text), onClick = onClick)
}
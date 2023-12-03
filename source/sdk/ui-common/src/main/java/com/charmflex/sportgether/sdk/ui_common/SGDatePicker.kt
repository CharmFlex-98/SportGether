package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.lang.Exception
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SGDatePicker(
    useCaseState: UseCaseState,
    onDismiss: () -> Unit,
    onConfirm: (LocalDateTime) -> Unit,
    date: LocalDateTime?,
    isVisible: Boolean
) {
    CalendarDialog(
        state = useCaseState,
        selection = CalendarSelection.Date(
            withButtonView = true,
            selectedDate = date?.toLocalDate(),
            onNegativeClick = onDismiss,
            onSelectDate = {
                onConfirm(it.atStartOfDay())
            }
        ),
        config = CalendarConfig(monthSelection = true)
    )

    if (isVisible) useCaseState.show()
    else useCaseState.hide()
}
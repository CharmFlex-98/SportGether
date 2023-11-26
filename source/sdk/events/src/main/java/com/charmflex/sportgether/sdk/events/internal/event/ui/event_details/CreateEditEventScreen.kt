package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.ui_common.SGDatePicker
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGMediumPrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

@Composable
internal fun CreateEditEventScreen(
    modifier: Modifier,
    viewModel: CreateEditEventViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    CreateEditEventScreenContent(
        modifier = modifier,
        viewState = viewState,
        isEdit = viewModel.isEdit(),
        onChooseDate = viewModel::onChooseDate,
        onEditField = viewModel::updateField,
        toggleCalendar = viewModel::toggleCalendar,
        onPrimaryButtonClick = viewModel::onClickEdit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateEditEventScreenContent(
    modifier: Modifier = Modifier,
    viewState: CreateEditEventViewState,
    isEdit: Boolean,
    onChooseDate: (String) -> Unit,
    onEditField: (CreateEditFieldInfo.FieldType, String) -> Unit,
    toggleCalendar: (isStartDate: Boolean, show: Boolean) -> Unit,
    onPrimaryButtonClick: () -> Unit,
) {
    SportGetherScaffold {

        val datePickerState = rememberDatePickerState()
        val timePickerState = rememberTimePickerState()
        val showDatePicker = viewState.datePickerState.showDatePicker

        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            val buttonText = getButtonText(isEdit)
            val nameField = viewState.nameField
            val placeField = viewState.placeField
            val startTimeField = viewState.startTimeField
            val endTimeField = viewState.endTimeField
            val maxCountField = viewState.maxParticipantField
            val descriptionField = viewState.descriptionField

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateEditTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = nameField.name,
                    hint = nameField.hint,
                    value = nameField.value,
                    fieldType = CreateEditFieldInfo.FieldType.NAME,
                    onEditField = onEditField
                )

                CreateEditTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = placeField.name,
                    hint = placeField.hint,
                    value = placeField.value,
                    fieldType = CreateEditFieldInfo.FieldType.DESTINATION,
                    onEditField = onEditField
                )
                Row {
                    CreateEditTextField(
                        modifier = Modifier
                            .padding(end = grid_x1)
                            .weight(1f)
                            .clickable {
                                toggleCalendar(true, true)
                            },
                        label = startTimeField.name,
                        hint = startTimeField.hint,
                        value = startTimeField.value,
                        fieldType = CreateEditFieldInfo.FieldType.START_TIME,
                        readOnly = true,
                        enable = false,
                        onEditField = onEditField
                    )
                    CreateEditTextField(
                        modifier = Modifier
                            .padding(start = grid_x1)
                            .weight(1f)
                            .clickable { toggleCalendar(false, true) },
                        label = endTimeField.name,
                        hint = endTimeField.hint,
                        value = endTimeField.value,
                        fieldType = CreateEditFieldInfo.FieldType.END_TIME,
                        readOnly = true,
                        enable = false,
                        onEditField = onEditField
                    )
                }
                CreateEditTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = maxCountField.name,
                    hint = maxCountField.hint,
                    value = maxCountField.value,
                    fieldType = CreateEditFieldInfo.FieldType.MAX_PARTICIPANT,
                    onEditField = onEditField
                )
                CreateEditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = grid_x1)
                        .weight(1f),
                    label = descriptionField.name,
                    hint = descriptionField.hint,
                    value = descriptionField.value,
                    fieldType = CreateEditFieldInfo.FieldType.DESCRIPTION,
                    onEditField = onEditField
                )

                SGLargePrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    onClick = onPrimaryButtonClick
                )
            }

            SGDatePicker(
                datePickerState = datePickerState,
                onDismiss = { toggleCalendar(true, false) },
                onConfirm = { onChooseDate(datePickerState.selectedDateMillis.toString()) },
                date = viewState.startTimeField.value.let { if (it.isEmpty()) null else it.toLong() },
                isVisible = showDatePicker
            )
        }
    }
}

@Composable
fun getButtonText(isEdit: Boolean): String {
    return when {
        isEdit -> stringResource(id = R.string.confirm_edit_event_button)
        else -> stringResource(id = R.string.confirm_host_event_button)
    }
}

@Composable
private fun CreateEditTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    hint: String,
    fieldType: CreateEditFieldInfo.FieldType,
    readOnly: Boolean = false,
    enable: Boolean = true,
    onEditField: (CreateEditFieldInfo.FieldType, String) -> Unit
) {
    SGTextField(
        modifier = modifier
            .padding(vertical = grid_x0_25),
        label = label,
        hint = hint,
        value = value,
        errorText = null,
        readOnly = readOnly,
        enable = enable,
        onValueChange = { onEditField(fieldType, it) }
    )
}
package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.toLocalDateTime
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.ui_common.SGDatePicker
import com.charmflex.sportgether.sdk.ui_common.SGDialog
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SGTimePicker
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import java.time.LocalDateTime

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
        onChooseTime = viewModel::onChooseTime,
        onEditField = viewModel::updateField,
        toggleCalendar = viewModel::toggleCalendar,
        toggleClock = viewModel::toggleClock,
        onBack = viewModel::back,
        onPrimaryButtonClick = viewModel::onPrimaryActionClick
    )
}

@Composable
internal fun CreateEditEventScreenContent(
    modifier: Modifier = Modifier,
    viewState: CreateEditEventViewState,
    isEdit: Boolean,
    onChooseDate: (LocalDateTime) -> Unit,
    onChooseTime: (Int, Int) -> Unit,
    onEditField: (CreateEditFieldPresentationModel.FieldType, String) -> Unit,
    toggleClock: (isShow: Boolean) -> Unit,
    toggleCalendar: (isShow: Boolean, isStartDate: Boolean) -> Unit,
    onBack: () -> Unit,
    onPrimaryButtonClick: () -> Unit,
) {
    SportGetherScaffold {
        val startDatePickerState = UseCaseState()
        val endDatePickerState = UseCaseState()
        val inUseDatePickerState =
            if (viewState.datePickerState.isStartDateChose) startDatePickerState else endDatePickerState
        val initialDate =
            if (viewState.datePickerState.isStartDateChose) viewState.startTimeField.value
            else viewState.endTimeField.value
        val timePicker = UseCaseState()
        val state = viewState.state

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
                    fieldType = CreateEditFieldPresentationModel.FieldType.NAME,
                    onEditField = onEditField
                )

                CreateEditTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = placeField.name,
                    hint = placeField.hint,
                    value = placeField.value,
                    fieldType = CreateEditFieldPresentationModel.FieldType.DESTINATION,
                    onEditField = onEditField
                )
                Row {
                    CreateEditTextField(
                        modifier = Modifier
                            .padding(end = grid_x1)
                            .weight(1f)
                            .clickable {
                                startDatePickerState.show()
                                toggleCalendar(true, true)
                            },
                        label = startTimeField.name,
                        hint = startTimeField.hint,
                        value = startTimeField.value,
                        fieldType = CreateEditFieldPresentationModel.FieldType.START_TIME,
                        readOnly = true,
                        enable = false,
                        onEditField = onEditField
                    )
                    CreateEditTextField(
                        modifier = Modifier
                            .padding(start = grid_x1)
                            .weight(1f)
                            .clickable {
                                endDatePickerState.show()
                                toggleCalendar(true, false)
                            },
                        label = endTimeField.name,
                        hint = endTimeField.hint,
                        value = endTimeField.value,
                        fieldType = CreateEditFieldPresentationModel.FieldType.END_TIME,
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
                    fieldType = CreateEditFieldPresentationModel.FieldType.MAX_PARTICIPANT,
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
                    fieldType = CreateEditFieldPresentationModel.FieldType.DESCRIPTION,
                    onEditField = onEditField
                )

                SGLargePrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    onClick = onPrimaryButtonClick
                )
            }

            SGDatePicker(
                useCaseState = inUseDatePickerState,
                onDismiss = { toggleCalendar(false, true) },
                onConfirm = {
                    onChooseDate(it)
                },
                date = initialDate.toLocalDateTime(pattern = DEFAULT_DATE_TIME_PATTERN),
                isVisible = viewState.datePickerState.isShowCalendar
            )

            SGTimePicker(
                timePickerState = timePicker,
                onDismiss = {
                    toggleClock(false)
                },
                onConfirm = onChooseTime,
                isVisible = viewState.datePickerState.isShowClock
            )

            when (viewState.state) {
                is CreateEditEventViewState.State.Loading -> CircularProgressIndicator()
                is CreateEditEventViewState.State.Success -> SGDialog(
                    title = stringResource(id = R.string.create_event_success_title),
                    text = stringResource(id = R.string.create_event_success_content),
                    onDismissRequest = { },
                    positiveText = stringResource(id = R.string.general_continue)
                ) {
                    onBack()
                }
                else -> {}
            }
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
    fieldType: CreateEditFieldPresentationModel.FieldType,
    readOnly: Boolean = false,
    enable: Boolean = true,
    onEditField: (CreateEditFieldPresentationModel.FieldType, String) -> Unit
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
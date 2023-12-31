package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.toLocalDateTime
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.ui_common.SGDatePicker
import com.charmflex.sportgether.sdk.ui_common.SGActionDialog
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGSnackBar
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SGTimePicker
import com.charmflex.sportgether.sdk.ui_common.SearchBottomSheet
import com.charmflex.sportgether.sdk.ui_common.SnackBarType
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.showSnackBarImmediately
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateEditEventScreen(
    modifier: Modifier,
    viewModel: CreateEditEventViewModel
) {
    val viewState by viewModel.viewState.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val onBottomSheetDismiss = {
        coroutineScope.launch {
            viewModel.resetBottomSheetState()
            bottomSheetState.hide()
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewState.bottomSheetState) {
        if (viewState.showBottomSheet()) bottomSheetState.show()
    }

    LaunchedEffect(key1 = viewState.error) {
        when (val e = viewState.error) {
            is UIErrorType.SnackBarMessageError -> snackbarHostState.showSnackBarImmediately(e.message)
            else -> {}
        }
    }

    CreateEditEventScreenContent(
        modifier = modifier,
        viewState = viewState,
        isEdit = viewModel.isEdit(),
        onChooseDate = viewModel::onChooseDate,
        onChooseTime = viewModel::onChooseTime,
        onEditField = viewModel::updateField,
        onTapDestinationField = viewModel::onTapDestField,
        toggleCalendar = viewModel::toggleCalendar,
        toggleClock = viewModel::toggleClock,
        onBack = viewModel::back,
        onPrimaryButtonClick = viewModel::onPrimaryActionClick
    )

    if (viewState.showBottomSheet()) {
        when (val state = viewState.bottomSheetState) {
            is CreateEditEventViewState.BottomSheetState.SearchState -> {
                SearchBottomSheet(
                    onDismiss = { onBottomSheetDismiss() },
                    searchFieldLabel = stringResource(id = com.charmflex.sportgether.sdk.ui_common.R.string.generic_search),
                    value = state.searchKey,
                    items = state.options,
                    errorText = "",
                    onChanged = { viewModel.updateSearchKey(it) }
                ) { _, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onSuggestedDestSelected(item.first, item.second)
                            }
                            .padding(grid_x1),
                        shape = RectangleShape,
                        elevation = CardDefaults.cardElevation(defaultElevation = grid_x1),
                        border = BorderStroke(grid_x0_25, color = Color.Black),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Box(
                            modifier = Modifier.padding(grid_x2)
                        ) {
                            Text(
                                text = item.second,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            else -> {
                // Do nothing
            }
        }
    }

    SGSnackBar(snackBarHostState = snackbarHostState, snackBarType = SnackBarType.Error)
}

@Composable
internal fun CreateEditEventScreenContent(
    modifier: Modifier = Modifier,
    viewState: CreateEditEventViewState,
    isEdit: Boolean,
    onChooseDate: (LocalDateTime) -> Unit,
    onChooseTime: (Int, Int) -> Unit,
    onEditField: (CreateEditFieldPresentationModel.FieldType, String) -> Unit,
    onTapDestinationField: () -> Unit,
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

        Box(
            modifier = modifier.scrollable(
                rememberScrollState(),
                orientation = Orientation.Vertical
            ), contentAlignment = Alignment.Center
        ) {
            val buttonText = getButtonText(isEdit)
            val nameField = viewState.nameField
            val placeField = viewState.placeField
            val startTimeField = viewState.startTimeField
            val endTimeField = viewState.endTimeField
            val maxCountField = viewState.maxParticipantField
            val descriptionField = viewState.descriptionField
            val interactionSource = remember { MutableInteractionSource() }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateEditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = grid_x2),
                    label = nameField.name,
                    hint = nameField.hint,
                    value = nameField.value,
                    fieldType = CreateEditFieldPresentationModel.FieldType.NAME,
                    onEditField = onEditField
                )

                CreateEditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = grid_x2),
                    value = placeField.value,
                    label = placeField.name,
                    hint = placeField.hint,
                    readOnly = true,
                    fieldType = CreateEditFieldPresentationModel.FieldType.DESTINATION,
                    onClicked = {
                        onTapDestinationField()
                    },
                    onEditField = onEditField,
                )
                CreateEditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = grid_x2),
                    label = startTimeField.name,
                    hint = startTimeField.hint,
                    value = startTimeField.value,
                    errorText = startTimeField.error,
                    fieldType = CreateEditFieldPresentationModel.FieldType.START_TIME,
                    readOnly = true,
                    onClicked = {
                        startDatePickerState.show()
                        toggleCalendar(true, true)
                    },
                    onEditField = onEditField
                )
                CreateEditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = grid_x2),
                    label = endTimeField.name,
                    hint = endTimeField.hint,
                    value = endTimeField.value,
                    errorText = endTimeField.error,
                    fieldType = CreateEditFieldPresentationModel.FieldType.END_TIME,
                    readOnly = true,
                    onClicked = {
                        endDatePickerState.show()
                        toggleCalendar(true, false)
                    },
                    onEditField = onEditField
                )
                CreateEditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = grid_x2),
                    label = maxCountField.name,
                    hint = maxCountField.hint,
                    value = maxCountField.value,
                    keyboardType = KeyboardType.Decimal,
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
                isVisible = viewState.datePickerState.isShowCalendar,
                boundary = LocalDate.now()..LocalDate.now().plusMonths(1)
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
                is CreateEditEventViewState.State.Success -> SGActionDialog(
                    title = stringResource(id = R.string.create_event_success_title),
                    text = stringResource(id = R.string.create_event_success_content),
                    onDismissRequest = { },
                    primaryButtonText = stringResource(id = R.string.generic_continue)
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
    errorText: String? = null,
    onClicked: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onEditField: (CreateEditFieldPresentationModel.FieldType, String) -> Unit
) {
    SGTextField(
        modifier = modifier
            .padding(vertical = grid_x0_25),
        label = label,
        hint = hint,
        value = value,
        errorText = errorText,
        readOnly = readOnly,
        enable = enable,
        onClicked = onClicked,
        keyboardType = keyboardType,
        onValueChange = { onEditField(fieldType, it) }
    )
}
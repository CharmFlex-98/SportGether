package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import android.media.metrics.Event
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x2

@Composable
internal fun CreateEditEventScreen(
    modifier: Modifier,
    viewModel: EventDetailsViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    CreateEditEventScreenContent(
        modifier = modifier,
        fields = viewState.value.fields,
        isEdit = viewModel.isEdit(),
        onEditField = viewModel::onEdit,
        onPrimaryButtonClick = viewModel::onPrimaryButtonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditEventScreenContent(
    modifier: Modifier = Modifier,
    fields: Map<EventDetailField.FieldType, EventDetailField>,
    isEdit: Boolean,
    onEditField: (EventDetailField.FieldType, String) -> Unit,
    onPrimaryButtonClick: () -> Unit,
) {
    SportGetherScaffold {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            val buttonText = getButtonText(isEdit)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                fields[EventDetailField.FieldType.NAME]?.let {
                    EventTextField(
                        modifier = Modifier.fillMaxWidth(),
                        field = it,
                        onEditField = onEditField
                    )
                }
                fields[EventDetailField.FieldType.DESTINATION]?.let {
                    EventTextField(
                        modifier = Modifier.fillMaxWidth(),
                        field = it,
                        onEditField = onEditField
                    )
                }
                Row {
                    fields[EventDetailField.FieldType.START_TIME]?.let {
                        EventTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = grid_x2), field = it, onEditField = onEditField
                        )
                    }
                    fields[EventDetailField.FieldType.END_TIME]?.let {
                        EventTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = grid_x2), field = it, onEditField = onEditField
                        )
                    }
                }
                fields[EventDetailField.FieldType.MAX_PARTICIPANT]?.let {
                    EventTextField(
                        modifier = Modifier.fillMaxWidth(),
                        field = it,
                        onEditField = onEditField
                    )
                }
                fields[EventDetailField.FieldType.DESCRIPTION]?.let {
                    EventTextField(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        field = it,
                        onEditField = onEditField
                    )
                }

                SGLargePrimaryButton(modifier = Modifier.fillMaxWidth(), text = buttonText, onClick = onPrimaryButtonClick)
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
private fun EventTextField(
    modifier: Modifier = Modifier,
    field: EventDetailField,
    onEditField: (EventDetailField.FieldType, String) -> Unit
) {
    SGTextField(
        modifier = modifier
            .padding(vertical = grid_x0_25),
        label = field.name,
        hint = field.hint,
        value = field.value,
        errorText = null,
        onValueChange = { onEditField(field.type, it) }
    )
}
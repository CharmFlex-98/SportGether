package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    val fields = viewState.value.fields

    EventDetailsScreenContent(modifier = modifier, fields = fields, onEdit = viewModel::onEdit)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreenContent(
    modifier: Modifier,
    fields: Map<EventDetailField.FieldType, EventDetailField>,
    onEdit: (EventDetailField.FieldType, String) -> Unit,
) {
    SportGetherScaffold {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Column {
                fields.values.forEach { field ->
                    SGTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = grid_x0_25),
                        label = field.name,
                        hint = field.hint,
                        value = field.value,
                        errorText = null,
                        onValueChange = { onEdit(field.type, it) }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun Preview() {
    val fields = listOf(
        EventDetailField(
            name = "Event Name",
            hint = "Event name that should enter",
            value = "",
            type = EventDetailField.FieldType.NAME
        ),
        EventDetailField(
            name = "Place",
            hint = "",
            value = "",
            type = EventDetailField.FieldType.DESTINATION
        ),
        EventDetailField(
            name = "Start",
            hint = "",
            value = "",
            type = EventDetailField.FieldType.START_TIME
        ),
        EventDetailField(
            name = "End",
            hint = "",
            value = "",
            type = EventDetailField.FieldType.END_TIME
        ),
        EventDetailField(
            name = "Max Participant",
            hint = "",
            value = "",
            type = EventDetailField.FieldType.MAX_PARTICIPANT
        ),
        EventDetailField(
            name = "Details",
            hint = "",
            value = "",
            type = EventDetailField.FieldType.DESCRIPTION
        )
    )
    EventDetailsScreenContent(modifier = Modifier.fillMaxSize().padding(grid_x2), fields = fields.associateBy { it.type }.toMap(), onEdit = { _, _ -> })
}
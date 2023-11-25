package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import android.media.metrics.Event
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventDetailField
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25

@Composable
internal fun CreateEditEventScreen(
    modifier: Modifier,
    viewModel: EventDetailsViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    CreateEditEventScreenContent(modifier = modifier, fields = viewState.value.fields, onEditField = viewModel::onEdit)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditEventScreenContent(
    modifier: Modifier = Modifier,
    fields: Map<EventDetailField.FieldType, EventDetailField>,
    onEditField: (EventDetailField.FieldType, String) -> Unit,
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
                        onValueChange = { onEditField(field.type, it) }
                    )

                }
            }
        }
    }
}
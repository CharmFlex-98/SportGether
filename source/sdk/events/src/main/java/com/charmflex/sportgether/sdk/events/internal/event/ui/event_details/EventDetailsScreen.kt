package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25

@Composable
internal fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    val fields = viewState.value.fields

    EventDetailsScreenContent(modifier = modifier, fields = fields)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventDetailsScreenContent(
    modifier: Modifier,
    fields: List<EventDetailFieldInfo>,
) {
    SportGetherScaffold {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Column {
                fields.forEach { field ->
                    SGTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = grid_x0_25),
                        label = field.name,
                        hint = "",
                        value = field.value,
                        errorText = null,
                        onValueChange = {  }
                    )
                }
            }
        }

    }
}
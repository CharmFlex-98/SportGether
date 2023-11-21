package com.charmflex.sportgether.app.home.ui.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.ui_common.ContentState
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.ListTableContentAlignment
import com.charmflex.sportgether.sdk.ui_common.WithState
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x4
import com.charmflex.sportgether.sdk.ui_common.grid_x7

@Composable
fun ScheduledEventBoard(
    modifier: Modifier,
    contentState: ContentState = ContentState.LoadingState,
    items: List<EventInfo> = listOf(),
    shownItemsMaxCount: Int = -1,
    contentColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    WithState(
        contentState = contentState,
        loadingState = { Text(text = "Loading") },
        emptyState = { Text("Empty") },
        errorState = { Text("Error") }) {
        ScheduleEventContent(
            modifier = modifier,
            items = items,
            shownItemsMaxCount = shownItemsMaxCount,
            contentColor = contentColor
        )
    }
}

@Composable
fun ScheduleEventContent(
    modifier: Modifier,
    items: List<EventInfo>,
    shownItemsMaxCount: Int,
    contentColor: Color
) {
    ListTable(
        modifier = modifier,
        items = items,
        alignment = ListTableContentAlignment.HORIZONTAL,
        shownItemMaxCount = shownItemsMaxCount,
    ) { index, item ->
        Card(
            modifier = Modifier
                .padding(grid_x1)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = grid_x1),
            border = BorderStroke(
                grid_x0_25, Color.Black,
            ),
            colors = CardDefaults.cardColors(containerColor = contentColor)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = item.eventName)
            }
        }
    }

}
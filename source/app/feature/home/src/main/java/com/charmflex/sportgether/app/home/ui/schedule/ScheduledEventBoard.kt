package com.charmflex.sportgether.app.home.ui.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.app.home.ui.event.EventBoardViewState
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import com.charmflex.sportgether.sdk.ui_common.ContentState
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.ListTableContentAlignment
import com.charmflex.sportgether.sdk.ui_common.WithState
import com.charmflex.sportgether.sdk.ui_common.grid_x0
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x0_5
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x2

@Composable
internal fun ScheduledEventBoard(
    modifier: Modifier,
    contentState: ContentState = ContentState.LoadingState,
    items: List<ScheduledEventInfoDomainModel> = listOf(),
    shownItemsMaxCount: Int = -1,
    contentColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Column(
        modifier = modifier,
    ) {
        Text(modifier = Modifier.padding(grid_x1), text = "Scheduled Event", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        WithState(
            contentState = contentState,
            loadingState = { Text(text = "Loading") },
            emptyState = { Text("Empty") },
            errorState = { Text("Error") }) {
            ScheduleEventContent(
                modifier = Modifier.fillMaxSize(),
                items = items,
                shownItemsMaxCount = shownItemsMaxCount,
                contentColor = contentColor,
            )
        }
    }
}

@Composable
internal fun ScheduleEventContent(
    modifier: Modifier,
    items: List<ScheduledEventInfoDomainModel>,
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
            Box(modifier = Modifier.fillMaxSize().padding(grid_x2), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = item.eventName, fontWeight = FontWeight.Bold)
                    Text(text = "In ${item.dayRemaining} days", fontWeight = FontWeight.Medium)
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = item.destination)
                            Text(text = item.startTime)
                        }
                    }
                }
            }
        }
    }

}
package com.charmflex.sportgether.app.home.ui.schedule

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.app.home.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.ScheduledEventInfoDomainModel
import com.charmflex.sportgether.sdk.ui_common.ContentState
import com.charmflex.sportgether.sdk.ui_common.ImageOverlay
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.ListTableContentAlignment
import com.charmflex.sportgether.sdk.ui_common.SGRoundCornerImage
import com.charmflex.sportgether.sdk.ui_common.WithState
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x10
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x3
import com.charmflex.sportgether.sdk.ui_common.grid_x5
import com.charmflex.sportgether.sdk.ui_common.grid_x9
import com.charmflex.sportgether.sdk.ui_common.shimmerEffect

@Composable
internal fun ScheduledEventBoard(
    modifier: Modifier,
    contentState: ContentState = ContentState.LoadingState,
    items: List<ScheduledEventInfoDomainModel> = listOf(),
    shownItemsMaxCount: Int = -1,
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(grid_x1),
            text = "Scheduled Event",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        WithState(
            contentState = contentState,
            loadingState = { ScheduledEventsLoadingState() },
            emptyState = { ScheduledEventEmptyContent() },
            errorState = { ScheduledEventErrorState() }) {
            ScheduleEventContent(
                modifier = Modifier.fillMaxSize(),
                items = items,
                shownItemsMaxCount = shownItemsMaxCount,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
internal fun ScheduleEventContent(
    modifier: Modifier,
    items: List<ScheduledEventInfoDomainModel>,
    shownItemsMaxCount: Int,
    onItemClick: (Int) -> Unit
) {
    ListTable(
        modifier = modifier,
        items = items,
        alignment = ListTableContentAlignment.HORIZONTAL,
        shownItemMaxCount = shownItemsMaxCount,
    ) { index, item ->
        Box(
            modifier = Modifier
                .clickable {
                    onItemClick(item.eventId)
                }
                .padding(grid_x1)
                .fillMaxSize()
        ) {
            ScheduledEventCard(modifier = Modifier.fillMaxSize(), item = item)
        }
    }
}

@Composable
private fun ScheduledEventCard(
    modifier: Modifier = Modifier,
    item: ScheduledEventInfoDomainModel,
) {
    SGRoundCornerImage(
        modifier = modifier,
        source = com.charmflex.sportgether.sdk.events.R.drawable.badminton_event_image
    )
    ImageOverlay(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(grid_x2))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(grid_x2),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = item.eventName,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 14.sp
        )
        Text(
            text = "In ${item.dayRemaining} days",
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 14.sp
        )
        Text(
            text = item.destination,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 11.sp,
            lineHeight = 11.sp
        )
        Text(
            text = item.startTime,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 11.sp,
            lineHeight = 11.sp
        )
    }
}

@Composable
private fun ScheduledEventsLoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ScheduledEventErrorState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(grid_x10),
                painter = painterResource(id = com.charmflex.sportgether.sdk.ui_common.R.drawable.error_image),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(grid_x2))
            Text(
                text = stringResource(R.string.scheduled_events_load_error),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ScheduledEventEmptyContent() {
    Box(
        modifier = Modifier
            .padding(grid_x1)
            .fillMaxSize()
            .border(
                border = BorderStroke(grid_x0_25, color = Color.Black), shape = RoundedCornerShape(
                    grid_x1
                )
            ), contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.scheduled_event_empty_state_text), fontWeight = FontWeight.Medium, fontSize = 16.sp)
    }
}
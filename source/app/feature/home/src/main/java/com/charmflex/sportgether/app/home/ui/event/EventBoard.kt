package com.charmflex.sportgether.app.home.ui.event

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.ui_common.ContentState
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.WithState
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x3
import com.charmflex.sportgether.sdk.ui_common.grid_x7
import com.charmflex.sportgether.sdk.ui_common.shimmerEffect
import com.charmflex.sportgether.app.home.R
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGMediumPrimaryButton

@Composable
fun EventBoard(
    modifier: Modifier = Modifier,
    contentState: ContentState,
    events: List<EventInfo>,
    shownEventMaxCount: Int = -1,
    contentColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    onHostEventClick: () -> Unit,
    onEventItemClick: (EventInfo) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(grid_x1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.event_board_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            SGMediumPrimaryButton(text = stringResource(id = R.string.host_event_button), onClick = onHostEventClick)
        }

        WithState(
            contentState = contentState,
            loadingState = {
                EventBoardLoadingContent()
            },
            emptyState = {
                EventBoardTextContent(text = stringResource(id = R.string.no_content))
            },
            errorState = {
                EventBoardTextContent(text = stringResource(id = R.string.event_data_load_error))
            }) {
            EventBoardContent(modifier = Modifier.fillMaxSize(), itemList = events, shownEventMaxCount = shownEventMaxCount, contentColor = contentColor, onItemClick = onEventItemClick)
        }
    }
}

@Composable
@Preview
fun EventBoardLoadingContent(
    modifier: Modifier = Modifier,
    fakeContentSize: Int = 3
) {
    ListTable(modifier = modifier, items = (1..fakeContentSize).toList()) { _, _ ->
        Card(
            modifier = Modifier.padding(vertical = grid_x1)
        ) {
            val mod = Modifier
                .fillMaxWidth()
                .height(grid_x7)
                .padding(grid_x2)
                .shimmerEffect()

            Column {
                Box(
                    modifier = mod
                )
                Box(
                    modifier = mod
                )
                Box(
                    modifier = mod
                )
            }
        }
    }
}

@Composable
internal fun EventBoardContent(
    modifier: Modifier = Modifier,
    itemList: List<EventInfo>,
    shownEventMaxCount: Int,
    contentColor: Color,
    onItemClick: (EventInfo) -> Unit
) {
    ListTable(modifier = modifier, items = itemList, shownItemMaxCount = shownEventMaxCount) { index, item ->
        EventInfoBar(eventInfo = item, contentColor = contentColor, onClick = onItemClick)
    }
}

@Composable
internal fun EventBoardTextContent(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(grid_x2))
            Text(
                text = text,
                color = Color.Red,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventInfoBar(
    modifier: Modifier = Modifier,
    eventInfo: EventInfo,
    contentColor: Color,
    onClick: (EventInfo) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = grid_x1),
        elevation = CardDefaults.cardElevation(defaultElevation = grid_x1),
        colors = CardDefaults.cardColors(containerColor = contentColor),
        onClick = { onClick(eventInfo) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(grid_x2),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(end = grid_x1),
                text = eventInfo.eventType.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = grid_x1),
                    text = eventInfo.eventName,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = grid_x1),
                    text = "Host: ${eventInfo.host.username}",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                )
            }
            EventDetailWidget(
                painterId = com.charmflex.sportgether.sdk.ui_common.R.drawable.ic_calendar,
                title = "Date",
                subtitle = "Date detail"
            )
            EventDetailWidget(
                painterId = com.charmflex.sportgether.sdk.ui_common.R.drawable.icon_destination,
                title = "Destination",
                subtitle = "Destination description"
            )
            EventDetailWidget(
                painterId = com.charmflex.sportgether.sdk.ui_common.R.drawable.icon_people,
                title = "Joiner",
                subtitle = "Joiner description"
            )
        }

    }
}

@Composable
private fun EventDetailWidget(
    @DrawableRes painterId: Int,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.padding(vertical = grid_x1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(grid_x3),
            painter = painterResource(id = painterId),
            contentDescription = null
        )
        Column {
            Text(
                modifier = Modifier.padding(start = grid_x2),
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(modifier = Modifier.padding(start = grid_x2), text = subtitle, fontSize = 16.sp)
        }
    }
}
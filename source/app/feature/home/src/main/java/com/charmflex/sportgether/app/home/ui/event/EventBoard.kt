package com.charmflex.sportgether.app.home.ui.event

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.sdk.core.utils.getViewModel
import com.charmflex.sportgether.sdk.events.internal.di.component.EventUIComponent
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventParticipantInfo
import com.charmflex.sportgether.sdk.events.internal.domain.models.EventType
import com.charmflex.sportgether.sdk.ui_common.ContentState
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.R
import com.charmflex.sportgether.sdk.ui_common.WithState
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x3
import com.charmflex.sportgether.sdk.ui_common.grid_x7
import com.charmflex.sportgether.sdk.ui_common.shimmerEffect
import java.time.LocalDateTime

@Composable
fun EventBoard(
    modifier: Modifier = Modifier,
    contentState: ContentState,
    events: List<EventInfo>
) {
    WithState(
        contentState = contentState,
        loadingState = {
            EventBoardLoadingContent()
        },
        emptyState = {
            EventBoardTextContent(text = stringResource(id = com.charmflex.sportgether.sdk.events.R.string.no_content))
        },
        errorState = {
            EventBoardTextContent(text = stringResource(id = com.charmflex.sportgether.sdk.events.R.string.load_error))
        }) {
        EventBoardContent(modifier = modifier, itemList = events)
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
            Column {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(grid_x7)
                        .padding(grid_x2)
                        .shimmerEffect()
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(grid_x7)
                        .padding(grid_x2)
                        .shimmerEffect()
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(fraction = 0.5f)
                        .height(grid_x7)
                        .padding(grid_x2)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
internal fun EventBoardContent(
    modifier: Modifier = Modifier,
    itemList: List<EventInfo>
) {
    ListTable(modifier = modifier, items = itemList) { index, item ->
        EventInfoBar(modifier = Modifier.fillMaxSize(), eventInfo = item)
    }
}

@Composable
internal fun EventBoardTextContent(
    modifier: Modifier = Modifier,
    text: String
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

@Composable
private fun EventInfoBar(
    modifier: Modifier = Modifier,
    eventInfo: EventInfo
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = grid_x1),
        elevation = CardDefaults.cardElevation(defaultElevation = grid_x1)
    ) {
        Column(
            modifier = Modifier.padding(grid_x2)
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
                painterId = R.drawable.ic_calendar,
                title = "Date",
                subtitle = "Date detail"
            )
            EventDetailWidget(
                painterId = R.drawable.icon_destination,
                title = "Destination",
                subtitle = "Destination description"
            )
            EventDetailWidget(
                painterId = R.drawable.icon_people,
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

@Preview
@Composable
fun EventBoardPreview() {
    val list = listOf(
        EventInfo(
            eventName = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = EventParticipantInfo(
                userId = 1,
                username = "JiaMing",
                profileIconUrl = ""
            ),
            joiners = listOf(
                EventParticipantInfo(
                    userId = 1,
                    username = "JiaMing",
                    profileIconUrl = ""
                ),
                EventParticipantInfo(
                    userId = 1,
                    username = "JiaMing",
                    profileIconUrl = ""
                ),
            )
        ),
        EventInfo(
            eventName = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = EventParticipantInfo(
                userId = 1,
                username = "JiaMing",
                profileIconUrl = ""
            ),
            joiners = listOf(
                EventParticipantInfo(
                    userId = 1,
                    username = "JiaMing",
                    profileIconUrl = ""
                ),
                EventParticipantInfo(
                    userId = 1,
                    username = "JiaMing",
                    profileIconUrl = ""
                ),
            )
        ),
        EventInfo(
            eventName = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = EventParticipantInfo(
                userId = 1,
                username = "JiaMing",
                profileIconUrl = ""
            ),
            joiners = listOf(
                EventParticipantInfo(
                    userId = 1,
                    username = "JiaMing",
                    profileIconUrl = ""
                ),
                EventParticipantInfo(
                    userId = 1,
                    username = "JiaMing",
                    profileIconUrl = ""
                ),
            )
        ),
    )
    EventBoardContent(itemList = list)
}
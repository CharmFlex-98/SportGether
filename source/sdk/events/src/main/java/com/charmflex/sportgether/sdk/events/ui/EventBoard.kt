package com.charmflex.sportgether.sdk.events.ui
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.sdk.events.domain.EventInfo
import com.charmflex.sportgether.sdk.events.domain.EventType
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x22_5
import com.charmflex.sportgether.sdk.ui_common.grid_x3
import java.time.LocalDateTime

@Composable
fun EventBoard(
    modifier: Modifier = Modifier,
    itemList: List<EventInfo>,
) {
    ListTable(modifier = modifier, items = itemList) { index, item ->
        EventInfoBar(modifier = Modifier.fillMaxSize(), eventInfo = item)
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
        elevation = CardDefaults.cardElevation(defaultElevation = grid_x1)) {
        Column(
            modifier = Modifier.padding(grid_x2)
        ) {
            Text(modifier = Modifier.padding(end = grid_x1), text = eventInfo.eventType.toString(), color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(end = grid_x1), text = eventInfo.theme, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.surfaceTint, fontSize = 24.sp)
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = grid_x1), text = "Host: ${eventInfo.host}", fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End)
            }
            EventDetailWidget(painterId = com.charmflex.sportgether.sdk.ui_common.R.drawable.ic_calendar, title = "Date", subtitle = "Date detail")
            EventDetailWidget(painterId = com.charmflex.sportgether.sdk.ui_common.R.drawable.icon_destination, title = "Destination", subtitle = "Destination description")
            EventDetailWidget(painterId = com.charmflex.sportgether.sdk.ui_common.R.drawable.icon_people, title = "Joiner", subtitle = "Joiner description")
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
        Icon(modifier = Modifier.size(grid_x3), painter = painterResource(id = painterId), contentDescription = null)
        Column {
            Text(modifier = Modifier.padding(start = grid_x2), text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(modifier = Modifier.padding(start = grid_x2), text = subtitle, fontSize = 16.sp)
        }
    }
}
@Preview
@Composable
fun EventInfoBarPreview() {
    val eventInfo = EventInfo(
        theme = "Bang bang",
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        place = "Seremban",
        eventType = EventType.BADMINTON,
        host = "Jia Ming",
        joiner = "Testing only"
    )
    EventInfoBar(eventInfo = eventInfo)
}

@Preview
@Composable
fun EventBoardPreview() {
    val list = listOf(
        EventInfo(
            theme = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            theme = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            theme = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            theme = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            theme = "Bang bang",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
    )
    EventBoard(itemList = list)
}
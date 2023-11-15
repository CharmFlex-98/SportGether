package com.charmflex.sportgether.sdk.events.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.events.domain.EventInfo
import com.charmflex.sportgether.sdk.events.domain.EventType
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x12
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import java.time.LocalDateTime

@Composable
internal fun EventBoard(
    modifier: Modifier = Modifier,
    itemList: List<EventInfo>,
) {
    ListTable(modifier = modifier, items = itemList) { item, content ->
        EventInfoBar(modifier = Modifier.fillMaxSize(), eventInfo = content)
    }
}

@Composable
internal fun EventInfoBar(
    modifier: Modifier = Modifier, 
    eventInfo: EventInfo
) {
    Card(modifier = modifier
        .padding(vertical = grid_x1)
        .fillMaxWidth()
        .height(grid_x12),
    elevation = CardDefaults.cardElevation(defaultElevation = grid_x1)) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = eventInfo.startTime.toString())
            Text(text = eventInfo.place)
            Text(text = eventInfo.host)
        }
    }
}

@Preview
@Composable
fun EventInfoBarPreview() {
    val eventInfo = EventInfo(
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
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            place = "Seremban",
            eventType = EventType.BADMINTON,
            host = "Jia Ming",
            joiner = "Testing only"
        ),
        EventInfo(
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
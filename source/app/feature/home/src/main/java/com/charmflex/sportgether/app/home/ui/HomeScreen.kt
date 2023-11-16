package com.charmflex.sportgether.app.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.events.domain.EventInfo
import com.charmflex.sportgether.sdk.events.domain.EventType
import com.charmflex.sportgether.sdk.events.ui.EventBoard
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x18
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import org.w3c.dom.Text
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen() {
    SportGetherScaffold {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(grid_x18),
                contentAlignment = Alignment.Center) {
                Text(text = "Some text here")
            }
            EventBoard(itemList = sampleData())
        }

    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}

fun sampleData(): List<EventInfo> {
    return listOf(
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
}
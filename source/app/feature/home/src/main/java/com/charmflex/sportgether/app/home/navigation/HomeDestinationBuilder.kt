package com.charmflex.sportgether.app.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.charmflex.sportgether.app.home.di.HomeUIComponent
import com.charmflex.sportgether.app.home.ui.event.EventBoard
import com.charmflex.sportgether.app.home.ui.schedule.ScheduledEventBoard
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.core.utils.getViewModel
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventParticipantInfo
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventType
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import com.charmflex.sportgether.sdk.navigation.routes.HomeRoutes
import com.charmflex.sportgether.sdk.ui_common.ContentState
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x4
import java.time.LocalDateTime

class HomeDestinationBuilder : DestinationBuilder {
    private val homeUIComponent by lazy { HomeUIComponent.injectCreate() }
    override fun NavGraphBuilder.buildGraph() {
        dashboardScreen()
    }

    private fun NavGraphBuilder.dashboardScreen() {
        composable(HomeRoutes.ROOT) {
            val viewModel = getViewModel { homeUIComponent.getEventBoardViewModel() }
            val viewState by viewModel.viewState.collectAsState()
            val shouldRefresh = it.savedStateHandle.remove<Boolean>(EventRoutes.Args.SHOULD_REFRESH) ?: false

            LaunchedEffect(Unit) {
                if (shouldRefresh) {
                    viewModel.refresh()
                }
            }

            SportGetherScaffold {
                Column {
                    ScheduledEventBoard(
                        modifier = Modifier
                            .weight(0.3f)
                            .fillMaxWidth(),
                        contentState = viewState.contentState,
                        items = viewState.eventDetail.filterIndexed { index, _ -> index < 5 },
                        shownItemsMaxCount = 2
                    )
                    Spacer(modifier = Modifier.height(grid_x4))
                    EventBoard(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(horizontal = grid_x2)
                            .fillMaxWidth(),
                        contentState = viewState.contentState,
                        events = viewState.eventDetail,
                        onHostEventClick = viewModel::onHostEventClick,
                        onEventItemClick = viewModel::onEventItemClick
                    )
                }
            }
        }
    }
}
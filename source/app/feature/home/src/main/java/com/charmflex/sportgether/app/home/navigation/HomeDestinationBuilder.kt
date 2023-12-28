package com.charmflex.sportgether.app.home.navigation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.charmflex.sportgether.app.home.di.HomeUIComponent
import com.charmflex.sportgether.app.home.ui.event.EventBoard
import com.charmflex.sportgether.app.home.ui.schedule.ScheduledEventBoard
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.core.utils.getViewModel
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import com.charmflex.sportgether.sdk.navigation.routes.HomeRoutes
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.grid_x4

class HomeDestinationBuilder(
    private val appContext: Context
) : DestinationBuilder {
    private val homeUIComponent by lazy { HomeUIComponent.injectCreate(appContext) }
    override fun NavGraphBuilder.buildGraph() {
        dashboardScreen()
    }

    private fun NavGraphBuilder.dashboardScreen() {
        composable(HomeRoutes.ROOT) {
            val eventViewModel = getViewModel { homeUIComponent.getEventBoardViewModel() }
            val scheduledViewModel = getViewModel { homeUIComponent.getScheduledEventBoardViewModel() }
            val eventViewState by eventViewModel.viewState.collectAsState()
            val scheduledEventViewState by scheduledViewModel.viewState.collectAsState()
            val pageViewState by eventViewModel.pageViewState.collectAsState()
            val shouldRefresh = it.savedStateHandle.remove<Boolean>(EventRoutes.Args.SHOULD_REFRESH) ?: false
            val refreshScheduledEvents = it.savedStateHandle.remove<Boolean>(EventRoutes.Args.SHOULD_REFRESH_SCHEDULED) ?: false

            LaunchedEffect(Unit) {
                if (shouldRefresh) {
                    eventViewModel.refresh()
                }
                if (refreshScheduledEvents) {
                    scheduledViewModel.refresh()
                }
            }

            SportGetherScaffold {
                Column(modifier = Modifier.fillMaxSize()) {
                    ScheduledEventBoard(
                        modifier = Modifier
                            .weight(0.3f)
                            .fillMaxWidth(),
                        contentState = scheduledEventViewState.contentState,
                        items = scheduledEventViewState.scheduleEvents,
                        shownItemsMaxCount = 2,
                        onItemClick = scheduledViewModel::navigateToEventDetail
                    )
                    Spacer(modifier = Modifier.height(grid_x4))
                    EventBoard(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(horizontal = grid_x2)
                            .fillMaxWidth(),
                        contentState = eventViewState.contentState,
                        events = eventViewState.eventDetail,
                        onHostEventClick = eventViewModel::onHostEventClick,
                        onBottomReach = eventViewModel::fetchMoreEvent,
                        isFetchingNext = pageViewState.isLoading,
                        onEventItemClick = eventViewModel::onEventItemClick
                    )
                }
            }
        }
    }
}
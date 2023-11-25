package com.charmflex.sportgether.sdk.events.internal.destination

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.core.utils.getViewModel
import com.charmflex.sportgether.sdk.events.internal.di.component.EventUIComponent
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditEventScreen
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailsScreen
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import com.charmflex.sportgether.sdk.ui_common.grid_x2

class EventDestinationBuilder : DestinationBuilder {
    private val eventUIComponent by lazy { EventUIComponent.injectCreate() }
    override fun NavGraphBuilder.buildGraph() {
        eventDetailsScreen()
        createEditEventScreen()
    }

    private fun NavGraphBuilder.eventDetailsScreen() {
        composable(
            route = EventRoutes.eventDetailsRoute,
            arguments = listOf(
                navArgument(EventRoutes.Args.EVENT_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val eventId = it.arguments?.getString(EventRoutes.Args.EVENT_ID)?.toInt()
            val eventDetailsViewModel = getViewModel { eventUIComponent.getEventDetailsViewModelFactory().create(eventId) }


            EventDetailsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(grid_x2),
                viewModel = eventDetailsViewModel
            )
        }
    }

    private fun NavGraphBuilder.createEditEventScreen() {
        composable(
            route = EventRoutes.createEditEventRoute,
            arguments = listOf(
                navArgument(EventRoutes.Args.EVENT_ID) {
                    type = NavType.StringType
                    nullable = true
                },
            )
        ) {
            val eventId = it.arguments?.getString(EventRoutes.Args.EVENT_ID)?.toInt()
            val createEditEventViewModel = getViewModel { eventUIComponent.getCreateEditEventViewModelFactory().create(eventId) }


            CreateEditEventScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(grid_x2),
                viewModel = createEditEventViewModel
            )
        }
    }
}
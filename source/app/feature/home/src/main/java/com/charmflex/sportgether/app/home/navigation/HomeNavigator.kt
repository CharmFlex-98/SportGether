package com.charmflex.sportgether.app.home.navigation

import com.charmflex.sportgether.sdk.core.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import javax.inject.Inject

internal interface HomeNavigator {

    fun toEventDetailScreen(eventId: Int)

    fun toHostEventScreen()
}

internal class HomeNavigatorImpl @Inject constructor(
    private val routeNavigator: RouteNavigator
) : HomeNavigator {
    override fun toEventDetailScreen(eventId: Int) {
        routeNavigator.navigateTo(EventRoutes.eventDetailsDestination(eventId))
    }

    override fun toHostEventScreen() {
        routeNavigator.navigateTo(EventRoutes.createEventScreen())
    }

}
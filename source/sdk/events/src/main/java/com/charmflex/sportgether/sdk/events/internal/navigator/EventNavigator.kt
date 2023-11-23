package com.charmflex.sportgether.sdk.events.internal.navigator

import com.charmflex.sportgether.sdk.core.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import javax.inject.Inject

internal interface EventNavigator {
    fun toCreateEventScreen()
}

internal class EventNavigatorImpl @Inject constructor(
    private val routeNavigator: RouteNavigator
) : EventNavigator {
    override fun toCreateEventScreen() {
        routeNavigator.navigateTo(EventRoutes.eventDetailScreen())
    }

}
package com.charmflex.sportgether.app.home.navigation

import com.charmflex.sportgether.sdk.core.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.routes.EventRoutes
import javax.inject.Inject

internal interface HomeNavigator {

    fun toEventDetailScreen()

    fun toHostEventScreen()
}

internal class HomeNavigatorImpl @Inject constructor(
    private val routeNavigator: RouteNavigator
) : HomeNavigator {
    override fun toEventDetailScreen() {
        routeNavigator.navigateTo(EventRoutes.eventDetailScreen())
    }

    override fun toHostEventScreen() {
        TODO("Not yet implemented")
    }

}
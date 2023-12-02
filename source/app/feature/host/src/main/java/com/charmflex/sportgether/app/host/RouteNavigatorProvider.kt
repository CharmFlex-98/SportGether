package com.charmflex.sportgether.app.host

import com.charmflex.sportgether.sdk.navigation.RouteNavigator

interface RouteNavigatorProvider {
    fun provideRouteNavigator(): RouteNavigator
}
package com.charmflex.sportgether.sdk.navigation.di

import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import dagger.Module
import dagger.Provides

@Module
interface RouteNavigatorModule {
    companion object {
        @Provides
        fun providesRouteNavigator(): RouteNavigator {
            return RouteNavigator.instance
        }
    }
}
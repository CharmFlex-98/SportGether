package com.charmflex.sportgether.app.home.di.modules

import com.charmflex.sportgether.app.home.navigation.HomeNavigator
import com.charmflex.sportgether.app.home.navigation.HomeNavigatorImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ToolsModule {

    @Binds
    fun bindsHomeNavigator(homeNavigatorImpl: HomeNavigatorImpl): HomeNavigator
}
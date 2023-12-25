package com.charmflex.sportgether.app.home.di

import com.charmflex.sportgether.app.home.di.modules.HomeUIModule
import com.charmflex.sportgether.app.home.di.modules.ToolsModule
import com.charmflex.sportgether.app.home.ui.HomeViewModel
import com.charmflex.sportgether.app.home.ui.event.EventBoardViewModel
import com.charmflex.sportgether.app.home.ui.schedule.ScheduledEventBoardViewModel
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.di.RouteNavigatorModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HomeUIModule::class,
        ToolsModule::class,
        RouteNavigatorModule::class
    ],
    dependencies = [MainInjector::class]
)
internal interface HomeUIComponent {

    @Component.Factory
    interface Factory {
        fun create(mainInjector: MainInjector): HomeUIComponent
    }

    companion object {
        fun injectCreate(): HomeUIComponent {
            return DaggerHomeUIComponent.factory().create(MainProvider.instance.getMainInjector())
        }
    }

    fun getHomeViewModel(): HomeViewModel

    fun getEventBoardViewModel(): EventBoardViewModel

    fun getScheduledEventBoardViewModel(): ScheduledEventBoardViewModel

    fun getRouteNavigator(): RouteNavigator
}
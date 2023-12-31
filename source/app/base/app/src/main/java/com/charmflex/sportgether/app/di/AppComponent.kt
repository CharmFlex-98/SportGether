package com.charmflex.sportgether.app.di

import android.content.Context
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.app.di.modules.NetworkModule
import com.charmflex.sportgether.app.di.modules.ToolsModule
import com.charmflex.sportgether.sdk.app_config.AppConfig
import com.charmflex.sportgether.sdk.app_config.AppConfigProvider
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.di.RouteNavigatorModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ToolsModule::class,
        NetworkModule::class,
        RouteNavigatorModule::class
    ]
)
interface AppComponent : MainInjector {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    companion object {
        fun injectCreate(context: Context): AppComponent {
            return DaggerAppComponent.factory().create(context)
        }
    }

    // Entry point to get a dependency
    // Needed if want to access dependency outside the dependency graph,
    // or if another component depend on dependency in this component.
    fun getRouteNavigator(): RouteNavigator
    fun getAppConfig(): AppConfig
}
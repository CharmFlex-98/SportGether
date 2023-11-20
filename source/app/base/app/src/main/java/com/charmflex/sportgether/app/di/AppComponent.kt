package com.charmflex.sportgether.app.di

import android.content.Context
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.app.di.modules.NetworkModule
import com.charmflex.sportgether.app.di.modules.ToolsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ToolsModule::class,
        NetworkModule::class,
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
    // fun dependency(): Dependency {
//         return something here
//     }
}
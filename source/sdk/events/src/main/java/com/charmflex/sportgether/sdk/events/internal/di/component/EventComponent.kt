package com.charmflex.sportgether.sdk.events.internal.di.component

import android.content.Context
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.di.modules.EventModule
import com.charmflex.sportgether.sdk.events.internal.di.modules.NetworkModule
import com.charmflex.sportgether.sdk.events.internal.di.modules.RepoModule
import com.charmflex.sportgether.sdk.events.internal.di.modules.ToolsModule
import com.charmflex.sportgether.sdk.events.internal.place.PlaceClientManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ToolsModule::class,
        RepoModule::class,
        NetworkModule::class,
        EventModule::class,
    ],
    dependencies = [MainInjector::class]
)
interface EventComponent {

    @Component.Factory
    interface Factory {
        fun create(mainInjector: MainInjector, @BindsInstance context: Context): EventComponent
    }

    companion object {

        fun injectCreate(context: Context): EventComponent {
            val mainProviderInstance = MainProvider.instance
            return DaggerEventComponent.factory().create(mainProviderInstance.getMainInjector(), context = context)
        }
    }

    // You see, this is the entry point to get singleton event service
    // This service will not be used in this component, as it will generate other instance of event service.
    // This component is separated out to provide service dependency only, without being consumed by the component itself.
    // So that each module will have and only have this event service singleton instance.
    fun getEventService(): EventService

    fun getPlaceClientManager(): PlaceClientManager
}
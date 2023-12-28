package com.charmflex.sportgether.sdk.events.internal.di.component

import android.content.Context
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import com.charmflex.sportgether.sdk.events.internal.di.modules.EventUIModule
import com.charmflex.sportgether.sdk.events.internal.di.modules.NetworkModule
import com.charmflex.sportgether.sdk.events.internal.di.modules.RepoModule
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditEventViewModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailsViewModel
import com.charmflex.sportgether.sdk.navigation.di.RouteNavigatorModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepoModule::class,
        EventUIModule::class,
        NetworkModule::class,
        RouteNavigatorModule::class
    ],
    dependencies = [MainInjector::class]
)
internal interface EventUIComponent {

    @Component.Factory
    interface Factory {
        fun create(mainInjector: MainInjector, @BindsInstance context: Context): EventUIComponent
    }

    companion object {
        fun injectCreate(context: Context): EventUIComponent {
            return DaggerEventUIComponent.factory().create(MainProvider.instance.getMainInjector(), context)
        }
    }

    fun getEventDetailsViewModelFactory(): EventDetailsViewModel.Factory

    fun getCreateEditEventViewModelFactory(): CreateEditEventViewModel.Factory
}
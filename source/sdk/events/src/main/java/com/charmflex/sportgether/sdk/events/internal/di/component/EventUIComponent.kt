package com.charmflex.sportgether.sdk.events.internal.di.component

import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import com.charmflex.sportgether.sdk.events.internal.di.modules.EventUIModule
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.CreateEditEventViewModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        EventUIModule::class
    ],
    dependencies = [MainInjector::class]
)
internal interface EventUIComponent {

    @Component.Factory
    interface Factory {
        fun create(mainInjector: MainInjector): EventUIComponent
    }

    companion object {
        fun injectCreate(): EventUIComponent {
            return DaggerEventUIComponent.factory().create(MainProvider.instance.getMainInjector())
        }
    }

    fun getEventDetailsViewModelFactory(): EventDetailsViewModel.Factory

    fun getCreateEditEventViewModelFactory(): CreateEditEventViewModel.Factory
}
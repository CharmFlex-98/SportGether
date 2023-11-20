package com.charmflex.sportgether.sdk.events.internal.di.component

import com.charmflex.sportgether.sdk.events.internal.di.modules.EventUIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        EventUIModule::class
    ]
)
interface EventUIComponent {

    @Component.Factory
    interface Factory {
        fun create(): EventUIComponent
    }

    companion object {
        fun injectCreate(): EventUIComponent {
            return DaggerEventUIComponent.factory().create()
        }
    }
}
package com.charmflex.sportgether.sdk.events.internal.di.component

import com.charmflex.sportgether.sdk.events.ServiceEventWrapper
import com.charmflex.sportgether.sdk.events.internal.di.modules.EventUIModule
import com.charmflex.sportgether.sdk.events.internal.ui.EventBoardViewModel
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

    fun getEventBoardViewModel(): EventBoardViewModel

    fun getServiceWrapper(): ServiceEventWrapper

}
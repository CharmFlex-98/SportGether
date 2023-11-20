package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.EventServiceFacade
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface EventUIModule {

    companion object {
        @Provides
        @Singleton
        fun providesEventService(): EventService {
            return EventService.getInstance()
        }
    }
}
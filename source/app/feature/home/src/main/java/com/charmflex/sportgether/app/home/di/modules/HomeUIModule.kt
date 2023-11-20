package com.charmflex.sportgether.app.home.di.modules

import com.charmflex.sportgether.sdk.events.EventService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HomeUIModule {
    companion object {
        @Singleton
        @Provides
        fun providesEventService(): EventService {
            return EventService.getInstance()
        }
    }
}
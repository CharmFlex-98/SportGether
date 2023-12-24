package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.AuthService
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.EventServiceFacade
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface EventModule {
    @Binds
    @Singleton
    fun bindsEventService(eventServiceFacade: EventServiceFacade): EventService

    companion object {
        @Provides
        @Singleton
        fun provideAuthService(): AuthService {
            return AuthService.getInstance()
        }
    }

}
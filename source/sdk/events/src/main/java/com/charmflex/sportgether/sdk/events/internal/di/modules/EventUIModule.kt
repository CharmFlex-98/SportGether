package com.charmflex.sportgether.sdk.events.internal.di.modules

import android.content.Context
import com.charmflex.sportgether.sdk.auth.internal.AuthService
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.EventServiceFacade
import com.charmflex.sportgether.sdk.events.internal.place.PlaceAutoCompleteExecutor
import com.charmflex.sportgether.sdk.events.internal.place.PlaceAutoCompleteExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface EventUIModule {

    @Binds
    @Singleton
    fun bindsPlaceAutoCompleteExecutor(placeAutoCompleteExecutorImpl: PlaceAutoCompleteExecutorImpl): PlaceAutoCompleteExecutor

    companion object {
        @Provides
        @Singleton
        fun providesEventService(context: Context): EventService {
            return EventService.getInstance(context)
        }

        @Provides
        fun provideAuthService(): AuthService {
            return AuthService.getInstance()
        }
    }
}
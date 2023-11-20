package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.EventServiceFacade
import com.charmflex.sportgether.sdk.events.internal.domain.repositories.EventRepository
import com.charmflex.sportgether.sdk.events.internal.domain.repositories.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface EventModule {
    @Binds
    @Singleton
    fun bindsEventService(eventServiceFacade: EventServiceFacade): EventService
}
package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface RepoModule {

    @Binds
    fun bindsEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

}
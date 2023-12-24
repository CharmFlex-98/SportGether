package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.internal.event.data.repository.EventRepositoryImpl
import com.charmflex.sportgether.sdk.events.internal.event.domain.repositories.EventRepository
import dagger.Binds
import dagger.Module

@Module
internal interface RepoModule {

    @Binds
    fun bindsEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

}
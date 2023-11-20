package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.internal.data.api.EventApi
import com.charmflex.sportgether.sdk.events.internal.data.mapper.EventInfoMapper
import com.charmflex.sportgether.sdk.events.internal.domain.repositories.EventRepository
import com.charmflex.sportgether.sdk.events.internal.domain.repositories.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface RepoModule {

    companion object {
        @Provides
        @JvmStatic
        fun providesEventRepository(api: EventApi, mapper: EventInfoMapper): EventRepository {
            return EventRepositoryImpl(api, mapper)
        }
    }

}
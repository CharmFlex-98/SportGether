package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.internal.place.GPlaceClientManagerImpl
import com.charmflex.sportgether.sdk.events.internal.place.PlaceClientManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface ToolsModule {
    @Binds
    @Singleton
    fun bindPlaceClientManager(managerImpl: GPlaceClientManagerImpl): PlaceClientManager
}
package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.network.NetworkClientBuilder
import dagger.Module
import dagger.Provides

@Module
internal interface NetworkModule {

    companion object {
        @Provides
        @JvmStatic
        fun providesEventApi(networkClientBuilder: NetworkClientBuilder): EventApi {
            return networkClientBuilder.buildApi(EventApi::class.java)
        }
    }
}
package com.charmflex.sportgether.sdk.events.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.AuthService
import com.charmflex.sportgether.sdk.auth.internal.network.AuthTokenInterceptor
import com.charmflex.sportgether.sdk.events.internal.event.data.api.EventApi
import com.charmflex.sportgether.sdk.network.NetworkClientBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun providesEventApi(
            networkClientBuilder: NetworkClientBuilder,
            authTokenInterceptor: AuthTokenInterceptor
        ): EventApi {
            return networkClientBuilder
                .addInterceptor(authTokenInterceptor)
                .buildApi(EventApi::class.java)
        }

        @Provides
        @JvmStatic
        fun providesAuthTokenInterceptor(authService: AuthService): AuthTokenInterceptor {
            return authService.provideAuthTokenInterceptor()
        }
    }
}
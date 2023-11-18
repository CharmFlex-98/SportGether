package com.charmflex.sportgether.app.di.modules

import com.charmflex.sportgether.sdk.app_config.AppConfig
import com.charmflex.sportgether.sdk.network.DefaultNetworkClientBuilder
import com.charmflex.sportgether.sdk.network.NetworkClientBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NetworkModule {
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun providesNetworkClientBuilder(appConfig: AppConfig): NetworkClientBuilder {
            return DefaultNetworkClientBuilder(appConfig = appConfig)
        }
    }
}
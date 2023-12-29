package com.charmflex.sportgether.app.di.modules
import com.charmflex.sportgether.app.configs.DefaultAppConfig
import com.charmflex.sportgether.sdk.app_config.AppConfig
import com.charmflex.sportgether.sdk.app_config.AppConfigProvider
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.RouteNavigatorImpl
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface ToolsModule {

    @Singleton
    @Binds
    fun bindKeyStorageProvider(keyStorageProviderImpl: KeyStorageProviderImpl): KeyStorageProvider

    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun providesAppConfig(): AppConfig {
            return DefaultAppConfig()
        }
    }
}
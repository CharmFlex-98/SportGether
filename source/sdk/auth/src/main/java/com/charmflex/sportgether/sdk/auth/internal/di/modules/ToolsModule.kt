package com.charmflex.sportgether.sdk.auth.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigatorImp
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface ToolsModule {

    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideGson(): Gson {
            return Gson()
        }
    }
}
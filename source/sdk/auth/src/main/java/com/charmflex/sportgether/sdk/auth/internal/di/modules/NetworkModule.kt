package com.charmflex.sportgether.sdk.auth.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.data.api.AuthApi
import com.charmflex.sportgether.sdk.auth.internal.data.errors.AuthApiErrorMapper
import com.charmflex.sportgether.sdk.network.ApiErrorMapper
import com.charmflex.sportgether.sdk.network.ApiErrorParser
import com.charmflex.sportgether.sdk.network.ApiErrorParserImpl
import com.charmflex.sportgether.sdk.network.NetworkClientBuilder
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface NetworkModule {

    @Singleton
    @Binds
    fun bindApiErrorParser(parser: ApiErrorParserImpl): ApiErrorParser

    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideAuthApi(): AuthApi {
            return NetworkClientBuilder.getRetrofit().create(AuthApi::class.java)
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideApiErrorMapper(): ApiErrorMapper {
            return AuthApiErrorMapper()
        }
    }
}
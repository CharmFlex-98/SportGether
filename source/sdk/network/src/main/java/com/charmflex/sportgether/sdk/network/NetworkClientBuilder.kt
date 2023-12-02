package com.charmflex.sportgether.sdk.network

import android.os.Debug
import android.util.Log
import com.charmflex.sportgether.sdk.app_config.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkClientBuilder {

    fun addInterceptor(interceptor: Interceptor): NetworkClientBuilder

    fun <T> buildApi(c: Class<T>): T

    fun buildRetrofit(): Retrofit

}

class DefaultNetworkClientBuilder(
    private val appConfig: AppConfig,
    private val interceptors: MutableList<Interceptor> = mutableListOf()
) : NetworkClientBuilder {

    private val baseUrl: String
        get() = appConfig.baseUrl

    override fun addInterceptor(interceptor: Interceptor): NetworkClientBuilder {
        interceptors.add(interceptor)
        return this
    }

    override fun <T> buildApi(c: Class<T>): T {
        return buildRetrofit().create(c)
    }

    override fun buildRetrofit(): Retrofit {
        return synchronized(this) {
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .apply {
                interceptors.forEach(this::addInterceptor)
                // more to come?
            }
            .addInterceptor(loggingInterceptor())
            .build()
    }

    private fun loggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor(this::log).apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private fun log(msg: String) {
        Log.d("HTTP::", msg)
    }
}
package com.charmflex.sportgether.sdk.network

import com.charmflex.sportgether.sdk.app_config.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkClientBuilder {

    fun addInterceptor(interceptor: Interceptor)

    fun <T> buildApi(c: Class<T>): T

    fun buildRetrofit(): Retrofit

}

class DefaultNetworkClientBuilder(
    private val appConfig: AppConfig,
    private val interceptors: MutableList<Interceptor> = mutableListOf()
) : NetworkClientBuilder {

    private val baseUrl: String
        get() = appConfig.baseUrl

    @Volatile
    var instance: Retrofit? = null
    override fun addInterceptor(interceptor: Interceptor) {
        interceptors.add(interceptor)
    }

    override fun <T> buildApi(c: Class<T>): T {
        return buildRetrofit().create(c)
    }

    override fun buildRetrofit(): Retrofit {
        return synchronized(this) {
            instance ?: Retrofit
                .Builder()
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().also { instance = it }
        }
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .apply {
                interceptors.forEach(this::addInterceptor)
                // more to come?
            }
            .build()
    }
}
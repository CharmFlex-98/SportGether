package com.charmflex.sportgether.sdk.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkClientBuilder {

    fun buildRetrofit(): Retrofit

    companion object {
        fun getRetrofit(): Retrofit {
            return DefaultNetworkClientBuilder().buildRetrofit()
        }
    }
}

private class DefaultNetworkClientBuilder : NetworkClientBuilder {

    private val baseUrl = "http://10.0.2.2:8080"

    @Volatile
    var instance: Retrofit? = null

    override fun buildRetrofit(): Retrofit {
        return synchronized(this) {
            instance ?: Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().also { instance = it }
        }
    }
}
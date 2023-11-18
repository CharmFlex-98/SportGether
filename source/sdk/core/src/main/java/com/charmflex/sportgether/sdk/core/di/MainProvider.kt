package com.charmflex.sportgether.sdk.core.di

import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import com.charmflex.sportgether.sdk.network.NetworkClientBuilder

// This is the only singleton, that store our app instance.
// For easier access to Application() class and obtain shared info.
interface MainProvider {

    fun getMainInjector(): MainInjector

    companion object {
        lateinit var instance: MainProvider
    }
}

// For main dependencies
interface MainInjector {

    fun keyStorageProvider(): KeyStorageProvider
    fun getNetworkClientBuilder() : NetworkClientBuilder

}
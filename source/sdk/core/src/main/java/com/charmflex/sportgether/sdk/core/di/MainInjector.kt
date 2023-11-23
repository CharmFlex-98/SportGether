package com.charmflex.sportgether.sdk.core.di

import com.charmflex.sportgether.sdk.core.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import com.charmflex.sportgether.sdk.network.NetworkClientBuilder

// For main dependencies
interface MainInjector {

    fun keyStorageProvider(): KeyStorageProvider
    fun getNetworkClientBuilder(): NetworkClientBuilder
    fun getNavigator(): RouteNavigator

}
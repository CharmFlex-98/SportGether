package com.charmflex.sportgether.sdk.core.di.modules

import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface ToolsModule {

    @Singleton
    @Binds
    fun bindKeyStorageProvider(keyStorageProviderImpl: KeyStorageProviderImpl): KeyStorageProvider
}
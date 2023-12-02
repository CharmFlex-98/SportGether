package com.charmflex.sportgether.sdk.auth.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepository
import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepositoryImp
import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.TokenRepository
import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.TokenRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @Binds
    fun bindAuthRepository(authRepositoryImp: AuthRepositoryImp): AuthRepository

    @Binds
    fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository
}
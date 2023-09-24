package com.charmflex.sportgether.sdk.auth.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepository
import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepositoryImp
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @Binds
    fun bindAuthRepository(authRepositoryImp: AuthRepositoryImp): AuthRepository
}
package com.charmflex.sportgether.sdk.auth.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.AuthService
import com.charmflex.sportgether.sdk.auth.internal.AuthServiceFacade
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface AuthModule {

    @Binds
    fun bindsAuthService(authServiceFacade: AuthServiceFacade): AuthService

}
package com.charmflex.sportgether.sdk.auth.internal

import com.charmflex.sportgether.sdk.auth.internal.di.AuthComponent
import com.charmflex.sportgether.sdk.auth.internal.di.AuthUIComponent
import com.charmflex.sportgether.sdk.auth.internal.network.AuthTokenInterceptor
import com.charmflex.sportgether.sdk.core.utils.SingletonHolder
import javax.inject.Inject

interface AuthService {
    fun provideAuthTokenInterceptor(): AuthTokenInterceptor

    companion object {
        fun getInstance(): AuthService {
            return AuthServiceFacade.getInstance()
        }
    }
}

internal class AuthServiceFacade @Inject constructor(
    private val authTokenInterceptor: AuthTokenInterceptor
) : AuthService {
    override fun provideAuthTokenInterceptor(): AuthTokenInterceptor {
        return authTokenInterceptor
    }

    companion object : SingletonHolder<AuthService>(
        {
            AuthComponent.injectCreate().authService()
        }
    )

}
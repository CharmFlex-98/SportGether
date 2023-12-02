package com.charmflex.sportgether.sdk.auth.internal.network

import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.TokenRepository
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

private const val AUTH_HEADER = "Authorization"
private const val JWT_TOKEN_PREFIX = "Bearer"

class AuthTokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val newRequest = builder.addAuthorizationHeader().build()

        return chain.proceed(newRequest)
    }

    private fun Request.Builder.addAuthorizationHeader(): Request.Builder {
        val token = tokenRepository.getJwtToken()
        token.takeIf { it.isNotEmpty() }?.let {
            addHeader(AUTH_HEADER, "$JWT_TOKEN_PREFIX $it")
        }

        return this
    }
}
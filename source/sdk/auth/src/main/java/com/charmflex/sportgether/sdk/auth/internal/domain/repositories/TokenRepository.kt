package com.charmflex.sportgether.sdk.auth.internal.domain.repositories
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import javax.inject.Inject

internal const val JWT_TOKEN_KEY = "JWT_TOKEN_KEY"


interface TokenRepository {
    fun setJwtToken(token: String)
    fun getJwtToken(): String
}

internal class TokenRepositoryImpl @Inject constructor(
    private val keyStorageProvider: KeyStorageProvider
) : TokenRepository{
    override fun setJwtToken(token: String) {
        keyStorageProvider.setString(JWT_TOKEN_KEY, token)
    }

    override fun getJwtToken(): String {
        return keyStorageProvider.getString(JWT_TOKEN_KEY)
    }
}
package com.charmflex.sportgether.sdk.auth.internal.domain.repositories
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import javax.inject.Inject

internal interface UserRepository {
    fun setLoginToken(token: String)
    fun getLoginToken(token: String)
}

internal class UserRepositoryImpl @Inject constructor(
    private val keyStorageProvider: KeyStorageProvider
) : UserRepository{
    override fun setLoginToken(token: String) {
        keyStorageProvider.setString(LOGIN_TOKEN_KEY, token)
    }

    override fun getLoginToken(token: String) {
        TODO("Not yet implemented")
    }

    companion object {
        const val LOGIN_TOKEN_KEY = "LOGIN_TOKEN_KEY"
    }
}
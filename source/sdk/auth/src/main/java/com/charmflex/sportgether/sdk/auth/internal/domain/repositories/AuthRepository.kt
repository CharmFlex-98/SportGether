package com.charmflex.sportgether.sdk.auth.internal.domain.repositories

import com.charmflex.sportgether.sdk.auth.internal.data.api.AuthApi
import com.charmflex.sportgether.sdk.auth.internal.data.errors.ApiErrorHandler
import com.charmflex.sportgether.sdk.auth.internal.data.models.LoginUserRequest
import com.charmflex.sportgether.sdk.auth.internal.data.models.RegisterUserRequest
import com.charmflex.sportgether.sdk.auth.internal.data.models.RegisterUserResponse
import javax.inject.Inject

internal interface AuthRepository {

    suspend fun registerUser(username: String, password: String, email: String): RegisterUserResponse

    suspend fun loginUser(username: String, password: String)
}

internal class AuthRepositoryImp @Inject constructor(
    private val api: AuthApi,
    private val errorHandler: ApiErrorHandler
) : AuthRepository {
    override suspend fun registerUser(username: String, password: String, email: String): RegisterUserResponse {
        val request = RegisterUserRequest(
            username = username,
            password = password,
            email = email
        )
        return errorHandler {
            api.registerUser(request)
        }
    }

    override suspend fun loginUser(username: String, password: String) {
        val request = LoginUserRequest(
            username = username,
            password = password
        )
        return errorHandler {
            api.loginUser(request)
        }
    }


}
package com.charmflex.sportgether.sdk.auth.internal.domain.repositories

import com.charmflex.sportgether.sdk.auth.internal.data.api.AuthApi
import com.charmflex.sportgether.sdk.auth.internal.data.errors.ApiErrorHandler
import com.charmflex.sportgether.sdk.auth.internal.data.models.LoginUserRequest
import com.charmflex.sportgether.sdk.auth.internal.data.models.LoginUserResponse
import com.charmflex.sportgether.sdk.auth.internal.data.models.RegisterUserRequest
import com.charmflex.sportgether.sdk.auth.internal.domain.LoginUserResponseDomainModel
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import javax.inject.Inject

internal interface AuthRepository {

    suspend fun registerUser(username: String, password: String, email: String)

    suspend fun loginUser(username: String, password: String): LoginUserResponseDomainModel
}
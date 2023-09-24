package com.charmflex.sportgether.sdk.auth.internal.data.api

import com.charmflex.sportgether.sdk.auth.internal.data.models.LoginUserRequest
import com.charmflex.sportgether.sdk.auth.internal.data.models.LoginUserResponse
import com.charmflex.sportgether.sdk.auth.internal.data.models.RegisterUserRequest
import com.charmflex.sportgether.sdk.auth.internal.data.models.RegisterUserResponse
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


internal interface AuthApi {

    @Headers("Content-Type: application/json")
    @POST("v1/auth/register")
    suspend fun registerUser(
        @Body body: RegisterUserRequest
    ): RegisterUserResponse

    @Headers("Content-Type: application/json")
    @POST("v1/auth/login")
    suspend fun loginUser(
        @Body body: LoginUserRequest
    ): LoginUserResponse

}
package com.charmflex.sportgether.sdk.auth.internal.data.models

internal data class RegisterUserRequest(
    val username: String,
    val email: String,
    val password: String
)
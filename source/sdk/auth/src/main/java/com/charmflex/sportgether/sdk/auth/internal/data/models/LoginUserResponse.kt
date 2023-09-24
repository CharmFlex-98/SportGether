package com.charmflex.sportgether.sdk.auth.internal.data.models

internal data class LoginUserResponse(
    val success: Boolean,
    val jwtToken: String
)
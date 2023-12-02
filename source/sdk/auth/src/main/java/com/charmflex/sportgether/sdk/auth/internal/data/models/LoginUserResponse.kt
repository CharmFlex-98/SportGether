package com.charmflex.sportgether.sdk.auth.internal.data.models

import com.google.gson.annotations.SerializedName

internal data class LoginUserResponse(
    @SerializedName("token")
    val token: String
)
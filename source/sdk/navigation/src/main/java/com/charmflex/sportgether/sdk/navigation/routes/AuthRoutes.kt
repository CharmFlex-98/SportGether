package com.charmflex.sportgether.sdk.navigation.routes

object AuthRoutes {
    const val ROOT = "/auth"

    fun login() = "$ROOT/login"

    fun register() = "$ROOT/register"

    fun resetPassword() = "$ROOT/reset_password"
}
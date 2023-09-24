package com.charmflex.sportgether.sdk.auth.internal.data.errors

sealed class AuthApiException : Exception()

object ExistingUserException : AuthApiException()
object ExistingUsernameException : AuthApiException()
object ExistingEmailException : AuthApiException()
object UserNotFoundException : AuthApiException()
object UnknownException : AuthApiException()
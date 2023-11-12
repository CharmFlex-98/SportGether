package com.charmflex.sportgether.sdk.auth.internal.data.errors

sealed class AuthApiException : Exception()
sealed class AuthenticationError : AuthApiException()
object UsernameNotFoundException : AuthenticationError()
object WrongPasswordException : AuthenticationError()
object ExistingUsernameException : AuthApiException()
object ExistingEmailException : AuthApiException()

object UnknownException : AuthApiException()

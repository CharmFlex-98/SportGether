package com.charmflex.sportgether.sdk.auth.internal.data.errors

import com.charmflex.sportgether.sdk.network.ApiErrorMapper
import com.charmflex.sportgether.sdk.network.ApiExceptionBody
import javax.inject.Inject


internal class AuthApiErrorMapper @Inject constructor()  : ApiErrorMapper {
    override fun map(apiExceptionBody: ApiExceptionBody): Exception {
        return when (apiExceptionBody.error.errorCode) {
            10001 -> ExistingUsernameException
            10002 -> ExistingEmailException
            10003 -> WrongPasswordException
            10004 -> UsernameNotFoundException
            else -> UnknownException
        }
    }
}
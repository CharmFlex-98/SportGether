package com.charmflex.sportgether.sdk.auth.internal.domain.usecases

import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepository
import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.TokenRepository
import com.charmflex.sportgether.sdk.core.utils.resultOf
import javax.inject.Inject

internal class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(username: String, password: String): Result<Unit> {
        return resultOf {
            authRepository.loginUser(username = username, password = password).let {
                tokenRepository.setJwtToken(it.token)
            }
        }
    }
}
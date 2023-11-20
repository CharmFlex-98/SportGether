package com.charmflex.sportgether.sdk.auth.internal.domain.usecases

import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepository
import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.UserRepository
import com.charmflex.sportgether.sdk.core.utils.resultOf
import javax.inject.Inject

internal class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(username: String, password: String): Result<Unit> {
        return resultOf {
            authRepository.loginUser(username = username, password = password).takeIf {
                it.success
            }?.let {
                userRepository.setLoginToken(it.jwtToken)
            }
        }
    }
}
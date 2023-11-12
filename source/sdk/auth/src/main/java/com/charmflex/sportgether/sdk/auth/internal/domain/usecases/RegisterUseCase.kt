package com.charmflex.sportgether.sdk.auth.internal.domain.usecases

import com.charmflex.sportgether.sdk.auth.internal.domain.repositories.AuthRepository
import com.charmflex.sportgether.sdk.core.resultOf
import javax.inject.Inject

internal class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String, email: String): Result<Unit> {
        return resultOf {
            authRepository.registerUser(
                username = username,
                password = password,
                email = email
            )
        }
    }
}
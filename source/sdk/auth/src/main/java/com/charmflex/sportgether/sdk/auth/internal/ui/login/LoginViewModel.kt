package com.charmflex.sportgether.sdk.auth.internal.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.auth.internal.data.errors.AuthenticationError
import com.charmflex.sportgether.sdk.auth.internal.data.errors.WrongPasswordException
import com.charmflex.sportgether.sdk.auth.internal.domain.usecases.LoginUseCase
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import com.charmflex.sportgether.sdk.core.UIErrorType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val navigator: AuthNavigator,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState = _viewState.asStateFlow()

    fun loginUser() {
        toggleLoading(true)
        viewModelScope.launch {
            loginUseCase(
                username = _viewState.value.username,
                password = _viewState.value.password
            ).fold(
                onSuccess = {
                    updateLoginState(true)
                    delay(3000)
                    navigator.toHomeScreen()
                },
                onFailure = { throwable ->
                    when (throwable) {
                        is AuthenticationError -> _viewState.update {
                            it.copy(errorType = UIErrorType.AuthenticationError)
                        }

                        else -> {
                            _viewState.update {
                                it.copy(
                                    errorType = UIErrorType.GenericError
                                )
                            }
                        }
                    }
                }
            )
        }
        toggleLoading(false)
    }

    fun resetError() {
        _viewState.update {
            it.copy(
                errorType = UIErrorType.None
            )
        }
    }

    private fun updateLoginState(success: Boolean) {
        _viewState.update {
            it.copy(
                success = success
            )
        }
    }

    fun onRegisterClicked() {
        navigator.toRegisterScreen()
    }

    fun onForgotPasswordClicked() {
        navigator.toResetPasswordScreen()
    }

    fun onUserNameChanged(username: String) {
        _viewState.update { it.copy(username = username) }
    }

    fun onPasswordChanged(password: String) {
        _viewState.update { it.copy(password = password) }
    }

    private fun toggleLoading(isLoading: Boolean) {
        _viewState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }
}

internal data class LoginViewState(
    val username: String = "",
    val password: String = "",
    val errorType: UIErrorType = UIErrorType.None,
    val isLoading: Boolean = false,
    val success: Boolean = false
) {
    fun hasError() = errorType != UIErrorType.None
}

package com.charmflex.sportgether.sdk.auth.internal.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.auth.internal.domain.usecases.LoginUserUseCase
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import com.charmflex.sportgether.sdk.core.UIErrorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val navigator: AuthNavigator,
    private val loginUserUseCase: LoginUserUseCase
): ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState = _viewState.asStateFlow()

    fun loginUser() {
        toggleLoading(true)
        viewModelScope.launch {
            loginUserUseCase(username = _viewState.value.username, password = _viewState.value.password).fold(
                onSuccess = {
                    Log.d("test", "login success!")
                },
                onFailure = {
                    _viewState.update {
                        it.copy(
                            errorType = UIErrorType.AuthenticationError
                        )
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
    val isLoading: Boolean = false
) {
    fun hasError() = errorType != UIErrorType.None
}

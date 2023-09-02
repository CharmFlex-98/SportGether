package com.charmflex.sportgether.sdk.auth.internal.ui.login

import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val navigator: AuthNavigator
): ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState = _viewState.asStateFlow()
    fun loginUser(username: String, password: String) {
        // TODO:
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
}

internal data class LoginViewState(
    val username: String = "",
    val password: String = "",
    val error: String? = null
)

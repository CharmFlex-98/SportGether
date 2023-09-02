package com.charmflex.sportgether.sdk.auth.internal.ui.register

import androidx.lifecycle.ViewModel
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val navigator: AuthNavigator
) : ViewModel() {

    private val _viewState = MutableStateFlow(RegisterViewState())
    val viewState = _viewState.asStateFlow()

    fun register() {
        // TODO:
    }

    fun onFieldValueChanged(type: RegisterTextFieldType, value: String) {
        when (type) {
            RegisterTextFieldType.USERNAME -> onUsernameChanged(value)
            RegisterTextFieldType.EMAIL -> onEmailChanged(value)
            RegisterTextFieldType.PASS -> onPasswordChanged(value)
            RegisterTextFieldType.CONFIRM_PASS -> onConfirmPasswordChanged(value)
        }
    }

    private fun onUsernameChanged(userName: String) {
        _viewState.update { it.copy(username = userName) }
    }

    private fun onEmailChanged(email: String) {
        _viewState.update { it.copy(email = email) }
    }

    private fun onPasswordChanged(password: String) {
        _viewState.update { it.copy(password = password) }
    }

    private fun onConfirmPasswordChanged(confirmPassword: String) {
        _viewState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun onBack() {
        navigator.pop()
    }


}

internal data class RegisterViewState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val error: String? = null
)
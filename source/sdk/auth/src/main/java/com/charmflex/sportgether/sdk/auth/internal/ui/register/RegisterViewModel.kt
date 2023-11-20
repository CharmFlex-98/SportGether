package com.charmflex.sportgether.sdk.auth.internal.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.auth.internal.data.errors.AuthApiException
import com.charmflex.sportgether.sdk.auth.internal.data.errors.ExistingEmailException
import com.charmflex.sportgether.sdk.auth.internal.data.errors.ExistingUsernameException
import com.charmflex.sportgether.sdk.auth.internal.domain.usecases.RegisterUseCase
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.ui_common.SnackBarType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val navigator: AuthNavigator,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(RegisterViewState())
    val viewState = _viewState.asStateFlow()

    private val _registerSuccessState = MutableStateFlow(false)
    val registerUserSuccessState = _registerSuccessState.asStateFlow()

    fun onRegisterClick() {
        if (isFormValid()) {
            register()
        }
    }

    private fun isFormValid(): Boolean {
        var res = true

        if (!isPasswordValid()) {
            appendTextFieldError(UIErrorType.InvalidPasswordError).also { res = false }
        }
        if (!identicalPassword()) {
            appendTextFieldError(UIErrorType.InvalidConfirmPassword).also { res = false }
        }

        if (!isEmailValid()) {
            appendTextFieldError(UIErrorType.InvalidEmailAddressError).also { res = false }
        }

        return res
    }

    private fun register() {
        if (!identicalPassword()) {
            appendTextFieldError(UIErrorType.InvalidConfirmPassword)
            return
        }

        toggleLoading(true)
        viewModelScope.launch {
            registerUseCase(
                username = _viewState.value.username,
                password = _viewState.value.password,
                email = _viewState.value.email
            ).fold(
                onSuccess = {
                    _registerSuccessState.emit(true)
                },
                onFailure = {val error = when (it) {
                        is AuthApiException -> mapApiException(it)
                        else -> UIErrorType.GenericError
                    }
                    _viewState.update { viewState ->
                        viewState.copy(
                            errorType = error
                        )
                    }
                }
            )
            toggleLoading(false)
        }
    }

    private fun identicalPassword(): Boolean {
        val pass1 = _viewState.value.password
        val pass2 = _viewState.value.confirmPassword

        return pass1 == pass2
    }

    private fun appendTextFieldError(textFieldError: UIErrorType.TextFieldError) {
        _viewState.update {
            it.copy(
                formError = it.formError + textFieldError
            )
        }
    }

    private fun isEmailValid(): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return regex.matches(viewState.value.email)
    }

    private fun isPasswordValid(): Boolean {
        return viewState.value.password.length >= 6
    }
    private fun clearTextFieldError() {
        _viewState.update {
            it.copy(
                formError = listOf()
            )
        }
    }
    private fun mapApiException(exception: AuthApiException): UIErrorType {
        return when (exception) {
            is ExistingUsernameException -> {
                appendTextFieldError(UIErrorType.UsernameIsUsedError)
                UIErrorType.None
            }
            is ExistingEmailException -> {
                appendTextFieldError(UIErrorType.EmailIsUsedError)
                UIErrorType.None
            }
            else -> UIErrorType.GenericError
        }
    }

    fun getSnackBarType(): SnackBarType {
        return if (_viewState.value.errorType == UIErrorType.None) SnackBarType.Success
        else SnackBarType.Error
    }
    private fun toggleLoading(isLoading: Boolean) {
        _viewState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    fun resetError() {
        _viewState.update {
            it.copy(
                errorType = UIErrorType.None
            )
        }
    }

    fun onFieldValueChanged(type: RegisterTextFieldType, value: String) {
        if (viewState.value.formError.isNotEmpty()) clearTextFieldError()

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
    val errorType: UIErrorType = UIErrorType.None,
    val isLoading: Boolean = false,
    val formError: List<UIErrorType.TextFieldError> = emptyList()
)
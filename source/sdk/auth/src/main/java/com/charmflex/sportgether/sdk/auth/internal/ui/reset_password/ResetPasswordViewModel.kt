package com.charmflex.sportgether.sdk.auth.internal.ui.reset_password

import androidx.lifecycle.ViewModel
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class ResetPasswordViewModel @Inject constructor(
    private val navigator: AuthNavigator
) : ViewModel() {

    private val _viewState = MutableStateFlow(ResetPasswordViewState())
    val viewState = _viewState.asStateFlow()

    fun onBack() {
        navigator.pop()
    }

    fun onEmailChanged(email: String) {
        _viewState.update {
            it.copy(
                email = email
            )
        }
    }

    fun onResetClick() {

    }

}

internal data class ResetPasswordViewState(
    val email: String = ""
)
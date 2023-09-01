package com.charmflex.sportgether.sdk.auth.internal.di

import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.reset_password.ResetPasswordViewModel
import dagger.Component

@Component
internal interface AuthComponent {

    fun loginViewModel(): LoginViewModel

    fun resetPasswordViewModel(): ResetPasswordViewModel
}


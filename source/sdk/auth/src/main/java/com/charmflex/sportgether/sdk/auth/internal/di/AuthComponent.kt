package com.charmflex.sportgether.sdk.auth.internal.di

import com.charmflex.sportgether.sdk.auth.internal.ui.LoginViewModel
import dagger.Component

@Component
internal interface AuthComponent {

    fun loginViewModel(): LoginViewModel
}


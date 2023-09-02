package com.charmflex.sportgether.sdk.auth.internal.di

import androidx.navigation.NavController
import com.charmflex.sportgether.sdk.auth.internal.di.modules.ToolsModule
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.register.RegisterViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.reset_password.ResetPasswordViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ToolsModule::class])
internal interface AuthComponent {

    fun loginViewModel(): LoginViewModel

    fun resetPasswordViewModel(): ResetPasswordViewModel

    fun registerViewModel(): RegisterViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance navController: NavController): AuthComponent
    }

    companion object {
        fun injectCreate(navController: NavController): AuthComponent {
            return DaggerAuthComponent.factory().create(navController)
        }
    }

}


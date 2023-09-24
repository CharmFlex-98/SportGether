package com.charmflex.sportgether.sdk.auth.internal.di

import androidx.navigation.NavController
import com.charmflex.sportgether.sdk.auth.internal.di.modules.NavigatorModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.NetworkModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.RepositoryModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.ToolsModule
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.register.RegisterViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.reset_password.ResetPasswordViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ToolsModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        NavigatorModule::class
    ]
)
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


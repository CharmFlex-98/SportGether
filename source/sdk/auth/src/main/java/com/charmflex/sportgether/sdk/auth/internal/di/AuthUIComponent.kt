package com.charmflex.sportgether.sdk.auth.internal.di

import androidx.navigation.NavController
import com.charmflex.sportgether.sdk.auth.internal.AuthService
import com.charmflex.sportgether.sdk.auth.internal.di.modules.NavigatorModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.NetworkModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.RepositoryModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.ToolsModule
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.register.RegisterViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.reset_password.ResetPasswordViewModel
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// TODO: Remove some redundant dependencie here

@Singleton
@Component(
    modules = [
        ToolsModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        NavigatorModule::class
    ],
    dependencies = [
        MainInjector::class
    ]
)
internal interface AuthUIComponent {

    fun loginViewModel(): LoginViewModel

    fun resetPasswordViewModel(): ResetPasswordViewModel

    fun registerViewModel(): RegisterViewModel


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance navController: NavController, mainInjector: MainInjector): AuthUIComponent
    }

    companion object {
        fun injectCreate(navController: NavController): AuthUIComponent {
            return DaggerAuthUIComponent.factory().create(navController, MainProvider.instance.getMainInjector())
        }
    }

}


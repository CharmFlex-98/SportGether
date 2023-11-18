package com.charmflex.sportgether.sdk.auth.internal.di

import androidx.navigation.NavController
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
internal interface AuthComponent {

    fun loginViewModel(): LoginViewModel

    fun resetPasswordViewModel(): ResetPasswordViewModel

    fun registerViewModel(): RegisterViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance navController: NavController, mainInjector: MainInjector): AuthComponent
    }

    companion object {
        fun injectCreate(navController: NavController): AuthComponent {
            return DaggerAuthComponent.factory().create(navController, MainProvider.instance.getMainInjector())
        }
    }

}


package com.charmflex.sportgether.sdk.auth.internal.di

import com.charmflex.sportgether.sdk.auth.internal.AuthService
import com.charmflex.sportgether.sdk.auth.internal.di.modules.AuthModule
import com.charmflex.sportgether.sdk.auth.internal.di.modules.RepositoryModule
import com.charmflex.sportgether.sdk.core.di.MainInjector
import com.charmflex.sportgether.sdk.core.di.MainProvider
import dagger.Component

@Component(
    modules = [
        AuthModule::class,
        RepositoryModule::class
    ],
    dependencies = [MainInjector::class]
)
internal interface AuthComponent {

    @Component.Factory
    interface Factory {
        fun create(mainInjector: MainInjector): AuthComponent
    }

    companion object {
        fun injectCreate(): AuthComponent {
            return DaggerAuthComponent.factory().create(MainProvider.instance.getMainInjector())
        }
    }

    fun authService(): AuthService
}
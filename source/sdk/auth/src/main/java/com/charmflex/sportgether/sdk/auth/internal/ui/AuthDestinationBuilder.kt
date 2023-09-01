package com.charmflex.sportgether.sdk.auth.internal.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.charmflex.sportgether.sdk.auth.internal.di.AuthComponent
import com.charmflex.sportgether.sdk.auth.internal.di.DaggerAuthComponent
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginScreen
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.core.DestinationBuilder
import com.charmflex.sportgether.sdk.core.getViewModel

class AuthDestinationBuilder(): DestinationBuilder {
    private val component: AuthComponent by lazy { DaggerAuthComponent.create() }


    override fun NavGraphBuilder.buildGraph() {
        navigation(startDestination = LOGIN_PATH, route = ROOT_PATH) {
            loginDestination()
            registerDestination()
            forgotPasswordDestination()
        }
    }

    private fun NavGraphBuilder.loginDestination() {
        return composable(LOGIN_PATH) {
            val viewModel: LoginViewModel = getViewModel { component.loginViewModel() }
            LoginScreen(viewModel = viewModel)
        }
    }

    private fun NavGraphBuilder.registerDestination() {
        return composable(REGISTER_PATH) {}
    }

    private fun NavGraphBuilder.forgotPasswordDestination() {
        return composable(FORGOT_PASSWORD_PATH) {}
    }

    companion object {
        const val ROOT_PATH = "/auth"
        private const val LOGIN_PATH = "$ROOT_PATH/login"
        private const val REGISTER_PATH = "$ROOT_PATH/register"
        private const val FORGOT_PASSWORD_PATH = "$ROOT_PATH/register"
    }
}
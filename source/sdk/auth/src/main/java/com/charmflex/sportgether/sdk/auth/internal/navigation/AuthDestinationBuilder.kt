package com.charmflex.sportgether.sdk.auth.internal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.charmflex.sportgether.sdk.auth.internal.di.AuthComponent
import com.charmflex.sportgether.sdk.auth.internal.di.DaggerAuthComponent
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginScreen
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.core.DestinationBuilder
import com.charmflex.sportgether.sdk.core.getViewModel
import com.charmflex.sportgether.sdk.navigation.routes.AuthRoutes

class AuthDestinationBuilder(navController: NavController): DestinationBuilder {
    private val component: AuthComponent by lazy { AuthComponent.injectCreate(navController = navController) }


    override fun NavGraphBuilder.buildGraph() {
        navigation(startDestination = AuthRoutes.login(), route = AuthRoutes.ROOT) {
            loginDestination()
            registerDestination()
            forgotPasswordDestination()
        }
    }

    private fun NavGraphBuilder.loginDestination() {
        return composable(AuthRoutes.login()) {
            val viewModel: LoginViewModel = getViewModel { component.loginViewModel() }
            LoginScreen(viewModel = viewModel)
        }
    }

    private fun NavGraphBuilder.registerDestination() {
        return composable(AuthRoutes.register()) {}
    }

    private fun NavGraphBuilder.forgotPasswordDestination() {
        return composable(AuthRoutes.resetPassword()) {}
    }
}
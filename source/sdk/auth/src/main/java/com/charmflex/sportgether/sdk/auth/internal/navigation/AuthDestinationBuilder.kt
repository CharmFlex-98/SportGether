package com.charmflex.sportgether.sdk.auth.internal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.charmflex.sportgether.sdk.auth.internal.di.AuthComponent
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginScreen
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.register.RegisterScreen
import com.charmflex.sportgether.sdk.auth.internal.ui.register.RegisterViewModel
import com.charmflex.sportgether.sdk.auth.internal.ui.reset_password.ResetPasswordScreen
import com.charmflex.sportgether.sdk.auth.internal.ui.reset_password.ResetPasswordViewModel
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.core.utils.getViewModel
import com.charmflex.sportgether.sdk.navigation.routes.AuthRoutes

class AuthDestinationBuilder(private val navController: NavController) : DestinationBuilder {
    private val component: AuthComponent by lazy { AuthComponent.injectCreate(navController = navController) }


    override fun NavGraphBuilder.buildGraph() {
        navigation(startDestination = AuthRoutes.login(), route = AuthRoutes.ROOT) {
            loginDestination()
            registerDestination()
            resetPasswordDestination()
        }
    }

    private fun NavGraphBuilder.loginDestination() {
        return composable(AuthRoutes.login()) {
            val viewModel: LoginViewModel = getViewModel { component.loginViewModel() }
            LoginScreen(viewModel = viewModel)
        }
    }

    private fun NavGraphBuilder.registerDestination() {
        return composable(AuthRoutes.register()) {
            val viewModel: RegisterViewModel = getViewModel { component.registerViewModel() }
            RegisterScreen(viewModel = viewModel, onBack = navController::popBackStack)
        }
    }

    private fun NavGraphBuilder.resetPasswordDestination() {
        return composable(AuthRoutes.resetPassword()) {
            val viewModel: ResetPasswordViewModel =
                getViewModel { component.resetPasswordViewModel() }
            ResetPasswordScreen(viewModel = viewModel)
        }
    }
}
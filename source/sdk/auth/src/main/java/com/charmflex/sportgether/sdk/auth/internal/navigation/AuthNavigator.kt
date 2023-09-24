package com.charmflex.sportgether.sdk.auth.internal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.charmflex.sportgether.sdk.navigation.navigateAndPopUpTo
import com.charmflex.sportgether.sdk.navigation.navigateTo
import com.charmflex.sportgether.sdk.navigation.routes.AuthRoutes
import com.charmflex.sportgether.sdk.navigation.routes.HomeRoutes
import javax.inject.Inject

internal interface AuthNavigator {

    fun toHomeScreen()
    fun toLoginScreen()
    fun toResetPasswordScreen()

    fun toRegisterScreen()

    fun backToLoginScreen()

    fun pop()
}

internal class AuthNavigatorImp @Inject constructor(private val navController: NavController) : AuthNavigator {
    override fun toHomeScreen() {
        navController.navigateAndPopUpTo(HomeRoutes.ROOT, navController.graph.findStartDestination().route)
    }

    override fun toLoginScreen() {
        navController.navigateTo(AuthRoutes.login())
    }

    override fun toResetPasswordScreen() {
        navController.navigate(AuthRoutes.resetPassword())
    }

    override fun toRegisterScreen() {
        navController.navigate(AuthRoutes.register())

    }

    override fun backToLoginScreen() {
        navController.navigateAndPopUpTo(AuthRoutes.login(), AuthRoutes.login())
    }

    override fun pop() {
        navController.popBackStack()
    }
}
package com.charmflex.sportgether.sdk.navigation

import androidx.navigation.NavController
import androidx.navigation.PopUpToBuilder

fun NavController.navigateTo(route: String) {
    navigate(route) {
        launchSingleTop = true
    }
}

fun NavController.navigateAndPopUpTo(route: String, popUpToRouteInclusive: String? = null) {
    navigate(route) {
        launchSingleTop = true

        if (popUpToRouteInclusive != null) {
            popUpTo(popUpToRouteInclusive) {
                inclusive = true
            }
        }

    }
}

fun NavController.popWithArgs(data: Map<String, Any>? = null) {
    data?.let { args ->
        this.previousBackStackEntry?.savedStateHandle?.let { savedStateHandler ->
            for (arg in args) {
                savedStateHandler[arg.key] = arg.value
            }
        }
    }

    popBackStack()
}
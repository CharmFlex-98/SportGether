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
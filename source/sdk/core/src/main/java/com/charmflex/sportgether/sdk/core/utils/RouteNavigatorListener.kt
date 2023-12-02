package com.charmflex.sportgether.sdk.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.charmflex.sportgether.sdk.core.navigation.NavigateTo
import com.charmflex.sportgether.sdk.core.navigation.Pop
import com.charmflex.sportgether.sdk.core.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.core.navigation.PopWithArguments

@Composable
fun RouteNavigatorListener(routeNavigator: RouteNavigator, navController: NavController) {
    LaunchedEffect(Unit) {
        routeNavigator.navigationEvent.collect {
            when (it) {
                is NavigateTo -> navController.navigateT(it.route)
                is Pop -> navController.popBackStack()
                is PopWithArguments -> navController.
            }
        }
    }
}
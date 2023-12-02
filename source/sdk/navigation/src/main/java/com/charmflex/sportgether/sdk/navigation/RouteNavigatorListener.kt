package com.charmflex.sportgether.sdk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.charmflex.sportgether.sdk.navigation.NavigateTo
import com.charmflex.sportgether.sdk.navigation.Pop
import com.charmflex.sportgether.sdk.navigation.RouteNavigator
import com.charmflex.sportgether.sdk.navigation.PopWithArguments

@Composable
fun RouteNavigatorListener(
    routeNavigator: RouteNavigator,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        routeNavigator.navigationEvent.collect {
            when (it) {
                is NavigateTo -> navController.navigateTo(it.route)
                is Pop -> navController.popBackStack()
                is PopWithArguments -> navController.popWithArgs(it.data)
            }
        }
    }
}
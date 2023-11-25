package com.charmflex.sportgether.sdk.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.charmflex.sportgether.sdk.core.navigation.NavigateTo
import com.charmflex.sportgether.sdk.core.navigation.NavigationEvent
import com.charmflex.sportgether.sdk.core.navigation.Pop
import com.charmflex.sportgether.sdk.core.navigation.RouteNavigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RouteNavigatorListener(routeNavigator: RouteNavigator, navController: NavController) {
    LaunchedEffect(Unit) {
        routeNavigator.navigationEvent.collectLatest {
            when (it) {
                is NavigateTo -> navController.navigate(it.route)
                is Pop -> navController.popBackStack()
            }
        }
    }
}
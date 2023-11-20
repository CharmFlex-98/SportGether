package com.charmflex.sportgether.app.home.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.charmflex.sportgether.app.home.ui.HomeScreen
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.navigation.routes.HomeRoutes

class HomeDestinationBuilder(private val navController: NavController) : DestinationBuilder {
    override fun NavGraphBuilder.buildGraph() {
        composable(HomeRoutes.ROOT) {
            HomeScreen()
        }
    }
}
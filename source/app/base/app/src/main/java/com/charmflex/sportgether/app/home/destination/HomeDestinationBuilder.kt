package com.charmflex.sportgether.app.home.destination

import androidx.navigation.compose.composable
import com.charmflex.sportgether.app.home.ui.HomeScreen
import com.charmflex.sportgether.sdk.core.DestinationBuilder

class HomeDestinationBuilder : DestinationBuilder {
    override fun androidx.navigation.NavGraphBuilder.buildGraph() {
        composable(ROOT) {
            HomeScreen()
        }
    }

    companion object {
        const val ROOT = "/home"
    }
}
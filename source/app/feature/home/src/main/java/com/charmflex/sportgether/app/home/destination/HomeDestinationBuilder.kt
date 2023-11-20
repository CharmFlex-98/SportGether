package com.charmflex.sportgether.app.home.destination

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.charmflex.sportgether.app.home.di.HomeUIComponent
import com.charmflex.sportgether.app.home.ui.event.EventBoard
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.core.utils.getViewModel
import com.charmflex.sportgether.sdk.navigation.routes.HomeRoutes
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x18

class HomeDestinationBuilder(private val navController: NavController) : DestinationBuilder {
    private val homeUIComponent by lazy { HomeUIComponent.injectCreate() }
    override fun NavGraphBuilder.buildGraph() {
        eventScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun NavGraphBuilder.eventScreen() {

        composable(HomeRoutes.ROOT) {
            val viewModel = getViewModel { homeUIComponent.getEventBoardViewModel() }
            val viewState by viewModel.viewState.collectAsState()

            SportGetherScaffold {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(grid_x18),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Some text here")
                    }
                    EventBoard(
                        contentState = viewState.contentState,
                        events = viewState.events
                    )
                }
            }
        }
    }
}
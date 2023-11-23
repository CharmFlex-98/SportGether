package com.charmflex.sportgether.app.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.charmflex.sportgether.app.home.navigation.HomeDestinationBuilder
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthDestinationBuilder
import com.charmflex.sportgether.sdk.core.utils.DestinationBuilder
import com.charmflex.sportgether.sdk.navigation.routes.AuthRoutes

class HostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SportGetherTheme {
                NavHost(navController = navController, startDestination = AuthRoutes.ROOT) {
                    createDestinations(navController).forEach {
                        with(it) { buildGraph() }
                    }
                }
            }
        }
    }

    private fun createDestinations(navController: NavController): List<DestinationBuilder> {
        return listOf(
            HomeDestinationBuilder(navController = navController),
            AuthDestinationBuilder(navController = navController)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SportGetherTheme {
        Greeting("Android")
    }
}

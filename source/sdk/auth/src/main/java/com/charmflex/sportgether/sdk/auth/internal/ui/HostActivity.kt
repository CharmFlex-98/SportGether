package com.charmflex.sportgether.sdk.auth.internal.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.charmflex.sportgether.sdk.auth.internal.di.DaggerAuthComponent
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginScreen
import com.charmflex.sportgether.sdk.auth.internal.ui.login.LoginViewModel
import com.charmflex.sportgether.sdk.core.DestinationBuilder
import com.charmflex.sportgether.sdk.core.getViewModel
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

class HostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SportGetherTheme {
                NavHost(navController = navController, startDestination = AuthDestinationBuilder.ROOT_PATH) {
                    createDestinations().forEach {
                        with(it) { buildGraph() }
                    }
                }
            }
        }
    }

    private fun createDestinations(): List<DestinationBuilder> {
        return listOf(
            AuthDestinationBuilder()
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
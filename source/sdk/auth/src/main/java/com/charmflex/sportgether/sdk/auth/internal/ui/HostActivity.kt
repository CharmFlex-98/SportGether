package com.charmflex.sportgether.sdk.auth.internal.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.auth.internal.di.AuthComponent
import com.charmflex.sportgether.sdk.auth.internal.di.DaggerAuthComponent
import com.charmflex.sportgether.sdk.core.getViewModel
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

class HostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerAuthComponent.create()
        setContent {
            SportGetherTheme {
                val viewModel: LoginViewModel = getViewModel { component.loginViewModel() }
                val viewModel1: LoginViewModel = getViewModel(factoryProvider = { component.loginViewModel()})

                Log.d("test", "$viewModel, $viewModel1")
                LoginScreen()
            }
        }
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
package com.charmflex.sportgether.app.splash.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.app.host.HostActivity
import com.charmflex.sportgether.app.splash.ui.theme.SportGetherTheme
import com.charmflex.sportgether.sdk.ui_common.LoaderWithBackground
import com.charmflex.sportgether.sdk.ui_common.LoadingAnimationSurface
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SplashViewModel by viewModels()
            val timer by viewModel.timer.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.startHostActivity.collectLatest {
                    Log.d("test", "start new activity")
                    startActivity(hostActivityIntent())
                    finish()
                }
            }

            SportGetherTheme {
                // A surface container using the 'background' color from the theme
                LoaderWithBackground()
            }
        }
    }

    private fun hostActivityIntent(): Intent {
        return Intent(this, HostActivity::class.java)
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
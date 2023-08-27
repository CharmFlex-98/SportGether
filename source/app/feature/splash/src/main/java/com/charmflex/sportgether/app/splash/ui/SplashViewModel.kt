package com.charmflex.sportgether.app.splash.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charmflex.sportgether.sdk.auth.internal.ui.HostActivity
import com.charmflex.sportgether.sdk.core.countDownTimer
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val SPLASH_PERIOD_IN_SECONDS = 5

class SplashViewModel: ViewModel() {
    private val _startHostActivityEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val startHostActivity = _startHostActivityEvent.asSharedFlow()

    var timer = MutableStateFlow(SPLASH_PERIOD_IN_SECONDS)
        private set

    init {
        viewModelScope.launch {
            countDownTimer(SPLASH_PERIOD_IN_SECONDS).collectLatest { t ->
                if (t == 0) _startHostActivityEvent.tryEmit(Unit)
                else timer.value = t
            }
        }
    }
}
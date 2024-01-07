package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
internal fun SGLottieAnimation(
    modifier: Modifier,
    composition: LottieComposition? = null,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true,
    reverseOnRepeat: Boolean = false,
    clipSpec: LottieClipSpec? = null,
    speed: Float = 1f,
    iterations: Int = 1
) {
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying,
        restartOnPlay,
        reverseOnRepeat,
        clipSpec,
        speed,
        iterations,
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress })
}

@Composable
fun LoadingAnimationSurface(
    modifier: Modifier = Modifier,
) {
    Surface(color = Color.Black.copy(alpha = 0.7f), modifier = Modifier.fillMaxSize()) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
        SGLottieAnimation(
            modifier = modifier,
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun SuccessGreenTickAnimation(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success_tick_anim))
    SGLottieAnimation(
        modifier = modifier.size(grid_x16),
        composition = composition,
        iterations = 1
    )
}

@Composable
fun Loader(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_dots_anim))
    SGLottieAnimation(
        modifier = modifier.size(grid_x10),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun LoaderWithBackground(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = 0.75f),
    ) {
        Loader(modifier = modifier)
    }
}
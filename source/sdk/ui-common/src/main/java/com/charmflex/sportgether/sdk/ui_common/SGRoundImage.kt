package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SGRoundImage(
    modifier: Modifier = Modifier,
    source: Any,
) {
    SGImage(modifier = modifier, source = source, shape = CircleShape)
}

@Composable
fun SGRoundCornerImage(
    modifier: Modifier = Modifier,
    source: Any?,
) {
    SGImage(modifier = modifier, source = source, shape = RoundedCornerShape(grid_x2))
}

@Composable
private fun SGImage(
    modifier: Modifier = Modifier,
    source: Any?,
    shape: Shape
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(source).build(),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape),
        contentDescription = null
    )
}

@Composable
fun ImageOverlay(
    modifier: Modifier = Modifier,
    colorTop: Color = Color(0F, 0F, 0F, 0F),
    colorBottom: Color = Color(0F, 0F, 0F, 0.75F),
    alpha: Float = 0.5f,
    topStart: Dp = grid_x3,
    topEnd: Dp = grid_x3,
    bottomEnd: Dp = grid_x3,
    bottomStart: Dp = grid_x3
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(colorTop, colorBottom)
                ),
                shape = RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart)
            )
            .alpha(alpha)
    ) {}
}

@Composable
@Preview
private fun RoundImagePreview() {
    SGRoundImage(source = "source/sdk/ui-common/src/main/assets/sample.jpeg")
}
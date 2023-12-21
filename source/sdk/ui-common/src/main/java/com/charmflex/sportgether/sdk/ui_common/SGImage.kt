package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SGRoundImage(
    modifier: Modifier = Modifier,
    filePath: Any,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(filePath).build(),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape),
        contentDescription = null
    )
}

@Composable
@Preview
private fun RoundImagePreview() {
    SGRoundImage(filePath = "source/sdk/ui-common/src/main/assets/sample.jpeg")
}
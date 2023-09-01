package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class ButtonSize {
    SMALL, MEDIUM, LARGE
}

private fun ButtonSize.getButtonHeight(): Dp {
    return when (this) {
        ButtonSize.SMALL -> grid_x4
        ButtonSize.MEDIUM -> grid_x5
        ButtonSize.LARGE -> grid_x7
    }
}


private fun ButtonSize.getFontSize(): TextUnit {
    return when (this) {
        ButtonSize.SMALL -> 14.sp
        ButtonSize.MEDIUM -> 16.sp
        ButtonSize.LARGE -> 18.sp
    }
}

@Composable
fun SGButtonGroupVertical(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(grid_x2),
        content = content
    )
}

@Composable
fun SGLargePrimaryButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    SGPrimaryButton(
        modifier = modifier,
        text = text,
        buttonSize = ButtonSize.LARGE,
        onClick = onClick
    )
}

@Composable
fun SGLargeSecondaryButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    SGSecondaryButton(
        modifier = modifier,
        text = text,
        buttonSize = ButtonSize.LARGE,
        onClick = onClick
    )
}

@Composable
private fun SGPrimaryButton(
    modifier: Modifier,
    text: String,
    buttonSize: ButtonSize,
    onClick: () -> Unit
) {
    Button(modifier = modifier.height(buttonSize.getButtonHeight()), onClick = onClick) {
        Text(
            text,
            style = TextStyle(fontSize = buttonSize.getFontSize(), fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
private fun SGSecondaryButton(
    modifier: Modifier,
    text: String,
    buttonSize: ButtonSize,
    onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = modifier.height(buttonSize.getButtonHeight()),
        onClick = onClick,
    ) {
        Text(
            text,
            style = TextStyle(fontSize = buttonSize.getFontSize(), fontWeight = FontWeight.SemiBold)
        )
    }
}


@Composable
@Preview
fun SGPrimaryButtonPreview() {
    SGPrimaryButton(modifier = Modifier, text = "Hello world", buttonSize = ButtonSize.LARGE) {

    }
}

@Composable
@Preview
fun SGSecondaryButtonPreview() {
    SGSecondaryButton(modifier = Modifier, text = "Hello world", buttonSize = ButtonSize.LARGE) {

    }
}




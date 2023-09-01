package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SGIconArrowBack() {
    Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = stringResource(id = R.string.icon_back))
}

@Preview
@Composable
fun Preview() {
    SGIconArrowBack()
}
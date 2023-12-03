package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

object SGIcons {
    @Composable
    fun ArrowBack(
        modifier: Modifier = Modifier
    ) {
        Icon(modifier = modifier, painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
    }

    @Composable
    fun Calendar(
        modifier: Modifier = Modifier
    ) {
        Icon(modifier = modifier, painter = painterResource(id = R.drawable.ic_calendar), contentDescription = null)
    }

    @Composable
    fun EmptyContent(
        modifier: Modifier = Modifier
    ) {
        Icon(modifier = modifier, painter = painterResource(id = R.drawable.ic_empty_content), contentDescription = null)
    }

    @Composable
    fun Destination(
        modifier: Modifier = Modifier
    ) {
        Icon(modifier = modifier, painter = painterResource(id = R.drawable.icon_destination), contentDescription = null)
    }

    @Composable
    fun People(
        modifier: Modifier = Modifier
    ) {
        Icon(modifier = modifier, painter = painterResource(id = R.drawable.icon_people), contentDescription = null)
    }

    @Composable
    fun RightArrowThin(
        modifier: Modifier = Modifier
    ) {
        Icon(modifier = modifier, painter = painterResource(id = R.drawable.ic_arrow_right_thin), contentDescription = null)
    }
}
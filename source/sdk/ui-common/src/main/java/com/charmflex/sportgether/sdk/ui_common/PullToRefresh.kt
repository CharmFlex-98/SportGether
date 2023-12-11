package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.charmflex.sportgether.sdk.ui_common.grid_x1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefresh(
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.nestedScroll(state.nestedScrollConnection)
    ) {
        content()
        PullToRefreshContainer(state = state)
    }

}
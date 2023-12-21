package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.ui_common.SGBasicTwoLineIconsItem
import com.charmflex.sportgether.sdk.ui_common.SGBasicTwoLineItem
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGRoundImage
import com.charmflex.sportgether.sdk.ui_common.SGSnackBar
import com.charmflex.sportgether.sdk.ui_common.SnackBarType
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x3
import com.charmflex.sportgether.sdk.ui_common.grid_x6
import com.charmflex.sportgether.sdk.ui_common.showSnackBarImmediately
import kotlinx.coroutines.delay

@Composable
internal fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    val fields = viewState.fields
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackbarMessage = when {
        viewState.joinSuccess -> stringResource(id = R.string.event_join_success_message)
        else -> ""
    }

    BackHandler(enabled = !viewState.joinSuccess) {
        viewModel.onBack()
    }

    LaunchedEffect(key1 = viewState.joinSuccess) {
        if (viewState.joinSuccess) {
            snackbarHostState.showSnackBarImmediately(snackbarMessage)
            viewModel.onBack()
        }
    }

    Box {
        EventDetailsScreenContent(modifier = modifier, fields = fields, onPrimaryButtonClick = viewModel::onPrimaryButtonClick)
        SGSnackBar(snackBarHostState = snackbarHostState, snackBarType = if (viewState.errorType is UIErrorType.None) SnackBarType.Success else SnackBarType.Error)
    }
}

@Composable
internal fun EventDetailsScreenContent(
    modifier: Modifier,
    fields: List<EventDetailContentInfo>,
    onPrimaryButtonClick: () -> Unit
) {
    SportGetherScaffold {
        Column(modifier = modifier) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column {
                    fields.forEach { field ->
                        SGBasicTwoLineItem(modifier = Modifier.padding(vertical = grid_x0_25), title = field.name, content = field.value)
                    }
                    SGBasicTwoLineIconsItem(
                        modifier = Modifier.padding(vertical = grid_x0_25),
                        title = "participant",
                        iconSize = grid_x6,
                        icons = listOf(
                            com.charmflex.sportgether.sdk.ui_common.R.drawable.chinese_boy_icon,
                            com.charmflex.sportgether.sdk.ui_common.R.drawable.chinese_girl_icon
                        )
                    )
                }
            }
            SGLargePrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.event_detail_join_button_text),
                onClick = onPrimaryButtonClick
            )
        }

    }
}
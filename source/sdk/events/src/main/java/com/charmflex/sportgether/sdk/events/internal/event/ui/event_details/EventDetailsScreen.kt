package com.charmflex.sportgether.sdk.events.internal.event.ui.event_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.ui_common.ListTable
import com.charmflex.sportgether.sdk.ui_common.SGBasicTwoLineIconsActionItem
import com.charmflex.sportgether.sdk.ui_common.SGBasicTwoLineItem
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGModalBottomSheet
import com.charmflex.sportgether.sdk.ui_common.SGRoundImage
import com.charmflex.sportgether.sdk.ui_common.SGSnackBar
import com.charmflex.sportgether.sdk.ui_common.SnackBarType
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x0_25
import com.charmflex.sportgether.sdk.ui_common.grid_x0_5
import com.charmflex.sportgether.sdk.ui_common.grid_x1
import com.charmflex.sportgether.sdk.ui_common.grid_x10
import com.charmflex.sportgether.sdk.ui_common.grid_x5
import com.charmflex.sportgether.sdk.ui_common.showSnackBarImmediately
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    val participantList by viewModel.participantViewState.collectAsState()
    val fields = viewState.fields
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val snackbarMessage = when {
        viewState.joinSuccess -> stringResource(id = R.string.event_join_success_message)
        else -> ""
    }
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = !viewState.joinSuccess) {
        viewModel.onBack()
    }

    LaunchedEffect(key1 = viewState.joinSuccess) {
        if (viewState.joinSuccess) {
            snackbarHostState.showSnackBarImmediately(snackbarMessage)
            viewModel.onBack()
        }
    }

    EventDetailsScreenContent(
        modifier = modifier,
        fields = fields,
        onCheckParticipants = viewModel::onCheckParticipants,
        onPrimaryButtonClick = viewModel::onPrimaryButtonClick
    )

    if (viewState.showBottomSheet()) {
        ParticipantsInfoBottomSheet(participantList = participantList) {
            coroutineScope.launch {
                bottomSheetState.hide()
                viewModel.resetBottomSheetState()
            }
        }
    }

    SGSnackBar(
        snackBarHostState = snackbarHostState,
        snackBarType = if (viewState.errorType is UIErrorType.None) SnackBarType.Success else SnackBarType.Error
    )

}

@Composable
internal fun EventDetailsScreenContent(
    modifier: Modifier,
    fields: List<EventInfoPresentationModel>,
    onCheckParticipants: () -> Unit,
    onPrimaryButtonClick: () -> Unit
) {
    SportGetherScaffold {
        Column(modifier = modifier) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column {
                    fields.forEach { field ->
                        when (field) {
                            is EventDetailBasicPresentationModel -> {
                                SGBasicTwoLineItem(
                                    modifier = Modifier.padding(vertical = grid_x0_25),
                                    title = field.name,
                                    content = field.value
                                )
                            }

                            is EventParticipantDetailPresentationModel -> {
                                SGBasicTwoLineIconsActionItem(
                                    modifier = Modifier.padding(grid_x0_25),
                                    iconSize = grid_x5,
                                    title = field.name,
                                    icons = field.icons,
                                    joinedCount = field.icons.size,
                                    maxAvailableCount = field.maxAvailableCount,
                                    onClick = onCheckParticipants
                                )
                            }
                        }
                    }
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

@Composable
private fun ParticipantsInfoBottomSheet(
    participantList: List<ParticipantsData>,
    onDismiss: () -> Unit
) {
    SGModalBottomSheet(
        onDismiss = onDismiss
    ) {
        ListTable(items = participantList) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = grid_x0_5, horizontal = grid_x1)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(grid_x10), contentAlignment = Alignment.Center) {
                        if (item.icon != null) SGRoundImage(
                            source = item.icon, modifier = Modifier.size(
                                grid_x5
                            )
                        )
                    }
                    Text(
                        text = item.name,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        painter = painterResource(id = com.charmflex.sportgether.sdk.ui_common.R.drawable.ic_arrow_right_thin),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
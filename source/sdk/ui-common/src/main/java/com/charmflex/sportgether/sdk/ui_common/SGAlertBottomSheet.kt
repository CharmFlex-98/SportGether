package com.charmflex.sportgether.sdk.ui_common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

@Composable
fun SGAlertBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    onDismiss: () -> Unit,
    actionButtonLayout: @Composable (() -> Unit)?
) {
    SGModalBottomSheet(
        modifier = modifier,
        onDismiss = onDismiss,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(grid_x3))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subtitle,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(grid_x3))
            if (actionButtonLayout != null) actionButtonLayout()
        }
    }
}

@Composable
fun GenericErrorBottomSheet(
    onPrimaryButtonClick: () -> Unit
) {
    SGAlertBottomSheet(
        title = stringResource(R.string.generic_error_bottomsheet_title),
        subtitle = stringResource(id = R.string.generic_error_bottomsheet_subtitle),
        onDismiss = { }) {
        SGLargePrimaryButton(text = stringResource(R.string.generic_ok)) {
            onPrimaryButtonClick()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview3() {
    SportGetherTheme {
        ModalBottomSheet(onDismissRequest = { /*TODO*/ }, contentColor = Color.Red) {
            Column(
            ) {
                Text(text = "test", fontSize = 20.sp)
                Box(modifier = Modifier.weight(1f)) {
                    Text(text = "Test", fontSize = 16.sp)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SGModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .padding(grid_x2), contentAlignment = Alignment.Center) {
            content()
        }
    }
}
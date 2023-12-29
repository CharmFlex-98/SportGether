package com.charmflex.sportgether.sdk.auth.internal.ui.reset_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.auth.R
import com.charmflex.sportgether.sdk.ui_common.BasicTopBar
import com.charmflex.sportgether.sdk.ui_common.SGIcons
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.grid_x10
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

@Composable
internal fun ResetPasswordScreen(viewModel: ResetPasswordViewModel) {
    val viewState by viewModel.viewState.collectAsState()

    ResetPasswordScreenContent(
        onBack = viewModel::onBack,
        onEmailChanged = viewModel::onEmailChanged,
        email = viewState.email,
        onResetClick = viewModel::onResetClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResetPasswordScreenContent(
    email: String,
    onEmailChanged: (String) -> Unit,
    onBack: () -> Unit,
    onResetClick: () -> Unit,
) {
    SportGetherScaffold(
        topBar = {
            BasicTopBar(
                leadingIcon = { SGIcons.ArrowBack() },
                leadingIconAction = onBack
            )
        }
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            SGTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = grid_x2),
                label = stringResource(id = R.string.reset_password_email),
                hint = stringResource(id = R.string.reset_password_email_hint),
                value = email,
                onValueChange = onEmailChanged,
                keyboardType = KeyboardType.NumberPassword,
                errorText = null
            )
        }
        Spacer(modifier = Modifier.padding(grid_x10))

        SGLargePrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.reset_confirm),
            onClick = onResetClick
        )

    }
}

@Composable
@Preview
fun Preview() {
    SportGetherTheme {
        ResetPasswordScreenContent(
            onBack = {},
            onEmailChanged = {},
            email = "Jiaming@gmail.com",
            onResetClick = {}
        )
    }
}


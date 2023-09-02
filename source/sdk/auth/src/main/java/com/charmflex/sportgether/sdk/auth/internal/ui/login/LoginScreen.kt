package com.charmflex.sportgether.sdk.auth.internal.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.auth.R
import com.charmflex.sportgether.sdk.ui_common.SGButtonGroupVertical
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGLargeSecondaryButton
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme
import com.charmflex.sportgether.sdk.ui_common.grid_x3

@Composable
internal fun LoginScreen(viewModel: LoginViewModel) {
    val viewState by viewModel.viewState.collectAsState()

    LoginScreenContent(
        username = viewState.username,
        onUserNameChanged = viewModel::onUserNameChanged,
        password = viewState.password,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRegisterClicked = viewModel::onRegisterClicked,
        onForgotPasswordClicked = viewModel::onForgotPasswordClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenContent(
    username: String,
    onUserNameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    SportGetherScaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SGTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(id = R.string.login_username),
                hint = stringResource(id = R.string.enter_username_hint_text),
                value = username,
                onValueChange = onUserNameChanged
            )
            SGTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                label = stringResource(id = R.string.login_password),
                hint = stringResource(id = R.string.enter_password_hint_text),
                onValueChange = onPasswordChanged
            )
        }

        SGButtonGroupVertical {
            SGLargePrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.register_button_text
                ),
                onClick = onRegisterClicked
            )
            SGLargeSecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.forgot_password_text
                ),
                onClick = onForgotPasswordClicked
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    SportGetherTheme() {
        LoginScreenContent(
            username = "Jia Ming",
            onUserNameChanged = {},
            password = "12839749",
            onPasswordChanged = {},
            onRegisterClicked = {}) {

        }
    }
}
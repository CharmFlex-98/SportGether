package com.charmflex.sportgether.sdk.auth.internal.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.auth.R
import com.charmflex.sportgether.sdk.core.ui.UIErrorType
import com.charmflex.sportgether.sdk.ui_common.LoadingAnimationSurface
import com.charmflex.sportgether.sdk.ui_common.Loader
import com.charmflex.sportgether.sdk.ui_common.SGButtonGroupVertical
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGLargeSecondaryButton
import com.charmflex.sportgether.sdk.ui_common.SGSnackBar
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SnackBarType
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.SuccessStatusDialog
import com.charmflex.sportgether.sdk.ui_common.grid_x10
import com.charmflex.sportgether.sdk.ui_common.grid_x12
import com.charmflex.sportgether.sdk.ui_common.grid_x2
import com.charmflex.sportgether.sdk.ui_common.showSnackBarImmediately
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

@Composable
internal fun LoginScreen(viewModel: LoginViewModel) {
    val viewState by viewModel.viewState.collectAsState()

    val errorType = viewState.errorType
    val snackbarErrorType = if (viewState.hasError()) SnackBarType.Error else SnackBarType.Success
    val snackbarErrorMessage = when (errorType) {
        UIErrorType.AuthenticationError -> stringResource(id = R.string.snackbar_authentication_error)
        else -> stringResource(id = R.string.snackbar_generic_error)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorType) {
        if (viewState.hasError()) {
            snackbarHostState.showSnackBarImmediately(snackbarErrorMessage)
            viewModel.resetError()
        }
    }

    LoginScreenContent(
        username = viewState.username,
        onUserNameChanged = viewModel::onUserNameChanged,
        password = viewState.password,
        onPasswordChanged = viewModel::onPasswordChanged,
        onLoginClicked = viewModel::loginUser,
        onRegisterClicked = viewModel::onRegisterClicked,
        onForgotPasswordClicked = viewModel::onForgotPasswordClicked
    )

    when (viewState.loadState) {
        is LoginViewState.State.IsLoading -> LoadingAnimationSurface()
        is LoginViewState.State.Success -> {
            SuccessStatusDialog(
                title = stringResource(id = R.string.dialog_login_success_text),
                subtitle = stringResource(
                    id = R.string.dialog_login_preparing_content_text
                )
            ) {
                Loader()
            }
        }
        else -> {}
    }

    SGSnackBar(snackBarHostState = snackbarHostState, snackBarType = snackbarErrorType)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenContent(
    username: String,
    onUserNameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    SportGetherScaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(grid_x2)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SGTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = grid_x2),
                label = stringResource(id = R.string.login_username),
                hint = stringResource(id = R.string.enter_username_hint_text),
                value = username,
                onValueChange = onUserNameChanged,
                keyboardType = KeyboardType.Text,
                errorText = null
            )
            SGTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = grid_x2),
                value = password,
                label = stringResource(id = R.string.login_password),
                hint = stringResource(id = R.string.enter_password_hint_text),
                onValueChange = onPasswordChanged,
                keyboardType = KeyboardType.NumberPassword,
                errorText = null
            )
        }

        SGButtonGroupVertical(
            modifier = Modifier.padding(grid_x2)
        ) {
            SGLargePrimaryButton(
                modifier = Modifier.fillMaxWidth(), text = stringResource(
                    id = R.string.login_button_text
                ), onClick = onLoginClicked
            )
            SGLargeSecondaryButton(
                modifier = Modifier.fillMaxWidth(), text = stringResource(
                    id = R.string.register_button_text
                ), onClick = onRegisterClicked
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    SportGetherTheme() {
        LoginScreenContent(username = "Jia Ming",
            onUserNameChanged = {},
            password = "12839749",
            onPasswordChanged = {},
            onRegisterClicked = {},
            onLoginClicked = {},
            onForgotPasswordClicked = {})
    }
}

@Preview
@Composable
fun LoginDialogPreview() {
    SuccessStatusDialog(
        title = stringResource(id = R.string.dialog_login_success_text),
        subtitle = stringResource(id = R.string.dialog_login_preparing_content_text)
    ) {
        Loader()
    }
}
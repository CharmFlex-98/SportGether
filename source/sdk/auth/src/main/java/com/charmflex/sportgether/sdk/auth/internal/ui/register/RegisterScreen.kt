package com.charmflex.sportgether.sdk.auth.internal.ui.register

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
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
import com.charmflex.sportgether.sdk.ui_common.BasicTopBar
import com.charmflex.sportgether.sdk.ui_common.SGIconArrowBack
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGSnackBar
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.showSnackBarImmediately

@Composable
internal fun RegisterScreen(viewModel: RegisterViewModel, onBack: () -> Unit) {
    val viewState by viewModel.viewState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val isLoading = viewState.isLoading
    val errorType = viewState.errorType
    val snackbarErrorMessage = getSnackBarErrorMessage(errorType = errorType)
    val registerSuccessMessage = stringResource(id = R.string.snackbar_register_success)
    val registerSuccessState by viewModel.registerUserSuccessState.collectAsState()

    BackHandler {
        if (!registerSuccessState) onBack()
    }


    LaunchedEffect(registerSuccessState) {
        if (registerSuccessState) {
            snackbarHostState.showSnackbar(registerSuccessMessage)
            onBack()
        }
    }

    LaunchedEffect(errorType) {
        if (errorType is UIErrorType.SnackBarError) {
            snackbarHostState.showSnackBarImmediately(snackbarErrorMessage)
            viewModel.resetError()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        RegisterScreenContent(
            viewState = viewState,
            onValueChange = viewModel::onFieldValueChanged,
            onRegister = viewModel::onRegisterClick,
            onBack = { if (!registerSuccessState) viewModel.onBack() }
        )

        if (isLoading) CircularProgressIndicator()
    }

    SGSnackBar(snackBarHostState = snackbarHostState, snackBarType = viewModel.getSnackBarType())
}

@Composable
private fun getSnackBarErrorMessage(errorType: UIErrorType): String {
    return when (errorType) {
        UIErrorType.RegistrationError -> stringResource(id = R.string.snackbar_register_fail)
        UIErrorType.RegisteredAccountError -> stringResource(id = R.string.snackbar_registered_account_error)
        else -> stringResource(id = R.string.snackbar_generic_error)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterScreenContent(
    viewState: RegisterViewState,
    onValueChange: (RegisterTextFieldType, String) -> Unit,
    onRegister: () -> Unit,
    onBack: () -> Unit,
) {
    val textFieldErrors = viewState.formError
    SportGetherScaffold(
        topBar = { BasicTopBar(leadingIcon = { SGIconArrowBack() }, leadingIconAction = onBack) }
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column {
                for (item in allRegisterTextField()) {
                    val errorText = item.errorTextMap.keys.firstOrNull {
                        textFieldErrors.contains(it)
                    }?.let {
                        item.errorTextMap[it]
                    }

                    SGTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(id = item.labelId),
                        hint = stringResource(id = item.hintId),
                        keyboardType = item.keyboardType,
                        errorText = errorText,
                        value = when (item.type) {
                            RegisterTextFieldType.USERNAME -> viewState.username
                            RegisterTextFieldType.EMAIL -> viewState.email
                            RegisterTextFieldType.PASS -> viewState.password
                            RegisterTextFieldType.CONFIRM_PASS -> viewState.confirmPassword
                        }
                    ) {
                        onValueChange(item.type, it)
                    }
                }
            }
        }

        SGLargePrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.register_button_confirm),
            onClick = onRegister
        )
    }
}


@Composable
private fun allRegisterTextField(): List<RegisterTextFieldItem> {
    return listOf(
        RegisterTextFieldItem(
            labelId = R.string.register_username,
            hintId = R.string.register_username_hint,
            type = RegisterTextFieldType.USERNAME,
            keyboardType = KeyboardType.Text,
            errorTextMap = mapOf(
                UIErrorType.UsernameIsUsedError to stringResource(id = R.string.textfield_username_used_error),
                UIErrorType.InvalidUsernameError to stringResource(id = R.string.textfield_invalid_username_error)
            )
        ),
        RegisterTextFieldItem(
            labelId = R.string.register_email,
            hintId = R.string.register_email_hint,
            type = RegisterTextFieldType.EMAIL,
            keyboardType = KeyboardType.Text,
            errorTextMap = mapOf(
                UIErrorType.EmailIsUsedError to stringResource(id = R.string.textfield_email_is_used_error),
                UIErrorType.InvalidEmailAddressError to stringResource(id = R.string.textfield_invalid_email_error)
            )
        ),
        RegisterTextFieldItem(
            labelId = R.string.register_password,
            hintId = R.string.register_password_hint,
            type = RegisterTextFieldType.PASS,
            keyboardType = KeyboardType.Password,
            errorTextMap = mapOf(
                UIErrorType.InvalidPasswordError to stringResource(id = R.string.textfield_invalid_password_error)
            )
        ),
        RegisterTextFieldItem(
            labelId = R.string.register_confirm_password,
            hintId = R.string.register_confirm_password_hint,
            type = RegisterTextFieldType.CONFIRM_PASS,
            keyboardType = KeyboardType.Password,
            errorTextMap = mapOf(
                UIErrorType.InvalidConfirmPassword to stringResource(id = R.string.textField_invalid_confirm_password_error)
            )
        ),
    )
}

private data class RegisterTextFieldItem(
    @StringRes
    val labelId: Int,

    @StringRes
    val hintId: Int,

    val type: RegisterTextFieldType,

    val keyboardType: KeyboardType,

    val errorTextMap: Map<UIErrorType.TextFieldError, String> = mapOf()
)

internal enum class RegisterTextFieldType {
    USERNAME, EMAIL, PASS, CONFIRM_PASS,
}

@Preview
@Composable
fun Preview() {
    RegisterScreenContent(
        viewState = RegisterViewState(),
        onValueChange = { _, _ -> },
        onRegister = {},
        onBack = {}
    )
}
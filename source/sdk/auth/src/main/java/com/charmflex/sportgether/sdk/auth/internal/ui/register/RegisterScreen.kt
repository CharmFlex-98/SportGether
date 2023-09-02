package com.charmflex.sportgether.sdk.auth.internal.ui.register

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.auth.R
import com.charmflex.sportgether.sdk.ui_common.BasicTopBar
import com.charmflex.sportgether.sdk.ui_common.SGIconArrowBack
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGTextField
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold

@Composable
internal fun RegisterScreen(viewModel: RegisterViewModel) {
    val viewState by viewModel.viewState.collectAsState()

    RegisterScreenContent(
        viewState = viewState,
        onValueChange = viewModel::onFieldValueChanged,
        onRegister = viewModel::register,
        onBack = viewModel::onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterScreenContent(
    viewState: RegisterViewState,
    onValueChange: (RegisterTextFieldType, String) -> Unit,
    onRegister: () -> Unit,
    onBack: () -> Unit,
) {
    SportGetherScaffold(
        topBar = { BasicTopBar(leadingIcon = { SGIconArrowBack() }, leadingIconAction = onBack) }
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Column {
                for (item in allRegisterTextField()) {
                    SGTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(id = item.labelId),
                        hint = stringResource(id = item.hintId),
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
            type = RegisterTextFieldType.USERNAME
        ),
        RegisterTextFieldItem(
            labelId = R.string.register_email,
            hintId = R.string.register_email_hint,
            type = RegisterTextFieldType.EMAIL
        ),
        RegisterTextFieldItem(
            labelId = R.string.register_password,
            hintId = R.string.register_password_hint,
            type = RegisterTextFieldType.PASS
        ),
        RegisterTextFieldItem(
            labelId = R.string.register_confirm_password,
            hintId = R.string.register_confirm_password_hint,
            type = RegisterTextFieldType.CONFIRM_PASS
        ),
    )
}

private data class RegisterTextFieldItem(
    @StringRes
    val labelId: Int,

    @StringRes
    val hintId: Int,

    val type: RegisterTextFieldType
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
package com.charmflex.sportgether.sdk.auth.internal.ui.reset_password

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.ui_common.BasicTopBar
import com.charmflex.sportgether.sdk.ui_common.SGIconArrowBack
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme

@Composable
internal fun ResetPasswordScreen(viewModel: ResetPasswordViewModel) {
    ResetPasswordScreenContent(
        onBack = viewModel::onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResetPasswordScreenContent(
    onBack: () -> Unit
) {
    SportGetherScaffold(
        topBar = { BasicTopBar(
            leadingIcon = { SGIconArrowBack() },
            leadingIconAction = onBack
        ) }
    ) {

    }
}

@Composable
@Preview
fun Preview() {
    SportGetherTheme {
        ResetPasswordScreenContent(
            onBack = {}
        )
    }
}


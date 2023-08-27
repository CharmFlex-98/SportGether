package com.charmflex.sportgether.sdk.auth.internal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.charmflex.sportgether.sdk.auth.R
import com.charmflex.sportgether.sdk.ui_common.BasicTopBar
import com.charmflex.sportgether.sdk.ui_common.ButtonSize
import com.charmflex.sportgether.sdk.ui_common.SGButtonGroupVertical
import com.charmflex.sportgether.sdk.ui_common.SGLargePrimaryButton
import com.charmflex.sportgether.sdk.ui_common.SGLargeSecondaryButton
import com.charmflex.sportgether.sdk.ui_common.SportGetherScaffold
import com.charmflex.sportgether.sdk.ui_common.theme.SportGetherTheme
import com.charmflex.sportgether.sdk.ui_common.theme.grid_x3
import com.charmflex.sportgether.sdk.ui_common.theme.grid_x8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    SportGetherTheme {
        SportGetherScaffold {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = grid_x3),
                    value = stringResource(id = R.string.login_username),
                    onValueChange = {})
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stringResource(id = R.string.login_password),
                    onValueChange = {})
            }

            SGButtonGroupVertical {
                SGLargePrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        id = R.string.register_button_text
                    )
                ) {

                }
                SGLargeSecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        id = R.string.login_button_text
                    )
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
package com.jesusdmedinac.baubap.awesomelogin.signup.presentation.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jesusdmedinac.baubap.awesomelogin.R
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.FunctionNotAvailableAlertDialog
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.SimpleAlertDialog
import com.jesusdmedinac.baubap.awesomelogin.main.presentation.compose.EyeIconAnimation
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import com.jesusdmedinac.baubap.awesomelogin.signup.presentation.model.SignupScreenModel
import com.jesusdmedinac.baubap.awesomelogin.signup.presentation.model.SignupScreenSideEffect
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module

@OptIn(ExperimentalMaterial3Api::class)
class SignupScreen(
    private val email: String,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var isPasswordVisible by remember {
            mutableStateOf(false)
        }
        var passwordTextFieldValue by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var isFunctionNotAvailableAlertDialogVisible by remember {
            mutableStateOf(false)
        }
        var isUserSignedUpSuccessfullyAlertDialogVisible by remember {
            mutableStateOf(false)
        }
        var isUserSignedUpFailureAlertDialogVisible by remember {
            mutableStateOf(false)
        }

        val screenModel: SignupScreenModel = getScreenModel()
        val sideEffect by screenModel.container.sideEffectFlow.collectAsState(initial = SignupScreenSideEffect.Idle)

        LaunchedEffect(sideEffect) {
            when (sideEffect) {
                is SignupScreenSideEffect.UserSignedUpFailure -> {
                    isUserSignedUpFailureAlertDialogVisible = true
                }

                is SignupScreenSideEffect.UserSignedUpSuccessfully -> {
                    isUserSignedUpSuccessfullyAlertDialogVisible = true
                }

                else -> Unit
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(title = { }, navigationIcon = {
                    IconButton(onClick = {
                        navigator.pop()
                    }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null)
                    }
                })
            },
            modifier = Modifier.padding(8.dp)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.create_a_new_account),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("signup_screen_title")
                )
                Text(text = buildAnnotatedString {
                    append(stringResource(R.string.with_your_account))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(email)
                    }
                    append(stringResource(R.string.and_enjoy_our_functions))
                })
                TextField(
                    value = passwordTextFieldValue,
                    onValueChange = {
                        passwordTextFieldValue = it
                        screenModel.onPasswordChange(it.text)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("password_text_field"),
                    trailingIcon = {
                        EyeIconAnimation(
                            isPasswordVisible,
                            isPasswordVisibleChange = {
                                isPasswordVisible = it
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { screenModel.onSignupClick(email) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .testTag("signup_button")
                ) {
                    Text(text = stringResource(R.string.create_my_account))
                }
                Text(
                    text = stringResource(R.string.problems_to_create_account),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
                TextButton(
                    onClick = { isFunctionNotAvailableAlertDialogVisible = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = stringResource(R.string.contact_us))
                }
            }
        }
        FunctionNotAvailableAlertDialog(
            visible = isFunctionNotAvailableAlertDialogVisible,
            onDismissRequest = {
                isFunctionNotAvailableAlertDialogVisible = false
            }
        )
        UserSignedUpSuccessfullyAlertDialog(
            visible = isUserSignedUpSuccessfullyAlertDialogVisible,
            onDismissRequest = {
                isUserSignedUpSuccessfullyAlertDialogVisible = false
            }
        )
        UserSignedUpFailureAlertDialog(
            visible = isUserSignedUpFailureAlertDialogVisible,
            onDismissRequest = {
                isUserSignedUpFailureAlertDialogVisible = false
            }
        )
    }

    @Composable
    private fun UserSignedUpSuccessfullyAlertDialog(
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = stringResource(R.string.welcome),
            message = stringResource(R.string.new_account_created),
            confirmText = stringResource(R.string.excellent),
            visible,
            onDismissRequest
        )
    }

    @Composable
    private fun UserSignedUpFailureAlertDialog(
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = stringResource(R.string.unable_to_create_new_account),
            message = stringResource(R.string.try_again_later),
            confirmText = stringResource(R.string.try_again),
            visible,
            onDismissRequest
        )
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    KoinApplication(application = {
        modules(
            CoreModule().module,
            SignupModule().module,
        )
    }) {
        Navigator(screen = SignupScreen("email"))
    }
}
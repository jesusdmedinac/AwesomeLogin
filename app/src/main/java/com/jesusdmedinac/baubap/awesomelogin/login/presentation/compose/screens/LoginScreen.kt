package com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jesusdmedinac.baubap.awesomelogin.R
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.FunctionNotAvailableAlertDialog
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.SimpleAlertDialog
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.model.LoginScreenModel
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.model.LoginScreenSideEffect
import com.jesusdmedinac.baubap.awesomelogin.main.presentation.compose.EyeIconAnimation
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module

@OptIn(ExperimentalMaterial3Api::class)
class LoginScreen(
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
        var isUserLoggedInSuccessfullyAlertDialogVisible by remember {
            mutableStateOf(false)
        }
        var isUserLoggedInFailureAlertDialogVisible by remember {
            mutableStateOf(false)
        }
        val screenModel: LoginScreenModel = getScreenModel()
        val sideEffect by screenModel.container.sideEffectFlow.collectAsState(initial = LoginScreenSideEffect.Idle)

        LaunchedEffect(sideEffect) {
            when (sideEffect) {
                is LoginScreenSideEffect.UserLoggedInFailure -> {
                    isUserLoggedInFailureAlertDialogVisible = true
                }

                is LoginScreenSideEffect.UserLoggedInSuccessfully -> {
                    isUserLoggedInSuccessfullyAlertDialogVisible = true
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
                    text = stringResource(R.string.hight_again),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("login_screen_title")
                )
                Text(text = buildAnnotatedString {
                    append(stringResource(R.string.type_the_password))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(email)
                    }
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
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(onClick = {
                        isFunctionNotAvailableAlertDialogVisible = true
                    }) {
                        Text(text = stringResource(R.string.forgot_password))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { screenModel.onLoginClick(email) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .testTag("login_button")
                ) {
                    Text(text = stringResource(R.string.login))
                }
                Text(
                    text = stringResource(R.string.problems_to_sign_in),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                TextButton(
                    onClick = { isFunctionNotAvailableAlertDialogVisible = true },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
        UserLoggedInSuccessfullyAlertDialog(
            email = email,
            visible = isUserLoggedInSuccessfullyAlertDialogVisible,
            onDismissRequest = {
                isUserLoggedInSuccessfullyAlertDialogVisible = false
            }
        )
        UserLoggedInFailureAlertDialog(
            visible = isUserLoggedInFailureAlertDialogVisible,
            onDismissRequest = {
                isUserLoggedInFailureAlertDialogVisible = false
            }
        )
    }

    @Composable
    private fun UserLoggedInSuccessfullyAlertDialog(
        email: String,
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = stringResource(id = R.string.welcome),
            message = stringResource(R.string.your_password_matches, email),
            confirmText = stringResource(id = R.string.excellent),
            visible,
            onDismissRequest
        )
    }

    @Composable
    private fun UserLoggedInFailureAlertDialog(
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = stringResource(R.string.wrong_password),
            message = stringResource(R.string.try_to_type_it_again),
            confirmText = stringResource(id = R.string.try_again),
            visible,
            onDismissRequest
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    KoinApplication(application = {
        modules(
            CoreModule().module,
            LoginModule().module,
        )
    }) {
        Navigator(screen = LoginScreen("email"))
    }
}
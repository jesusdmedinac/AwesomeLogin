package com.jesusdmedinac.baubap.awesomelogin.signup.presentation.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jesusdmedinac.baubap.awesomelogin.home.presentation.compose.FunctionNotAvailableAlertDialog
import com.jesusdmedinac.baubap.awesomelogin.home.presentation.compose.SimpleAlertDialog
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens.LoginScreen
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.model.LoginScreenSideEffect
import com.jesusdmedinac.baubap.awesomelogin.signup.presentation.model.SignupScreenModel
import com.jesusdmedinac.baubap.awesomelogin.signup.presentation.model.SignupScreenSideEffect

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
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(text = "Crea una cuenta nueva", style = MaterialTheme.typography.titleLarge)
                Row {
                    Text(text = "Con tu correo ")
                    Text(text = email, fontWeight = FontWeight.Bold)
                    Text(text = " y disfruta de todas nuestras funciones")
                }
                TextField(
                    value = passwordTextFieldValue,
                    onValueChange = {
                        passwordTextFieldValue = it
                        screenModel.onPasswordChange(it.text)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(
                                if (isPasswordVisible) Icons.Default.CheckCircle else Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { screenModel.onSignupClick(email) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Crear mi cuenta")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "¿Problemas para crear tu cuenta o iniciar sesión?")
                    TextButton(onClick = { isFunctionNotAvailableAlertDialogVisible = true }) {
                        Text(text = "Contáctanos")
                    }
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
            title = "¡Bienvenido!",
            message = "Tu nueva cuenta fue creada",
            confirmText = "¡Excelente!",
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
            title = "No pudimos crear tu cuenta",
            message = "Vuelve a intentarlo en más tarde",
            confirmText = "Volver a intentarlo",
            visible,
            onDismissRequest
        )
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    Navigator(screen = SignupScreen("email"))
}
package com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.AlertDialog
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
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.model.LoginScreenModel
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.model.LoginScreenSideEffect

@OptIn(ExperimentalMaterial3Api::class)
class LoginScreen(
    private val email: String,
) : Screen {
    @Composable
    override fun Content() {
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
                TopAppBar(title = { /*TODO*/ }, navigationIcon = {})
            },
            modifier = Modifier.padding(8.dp)
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(text = "¡Hola de nuevo!", style = MaterialTheme.typography.titleLarge)
                Row {
                    Text(text = "Ingresa la contraseña de tu cuenta ")
                    Text(text = email, fontWeight = FontWeight.Bold)
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
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(onClick = {
                        isFunctionNotAvailableAlertDialogVisible = true
                    }) {
                        Text(text = "¿Olvidaste tu contraseña?")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { screenModel.onLoginClick(email) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Iniciar sesión")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "¿Problemas para iniciar sesión?")
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
        UserLoggedInSuccessfullyAlertDialog(
            email = email,
            visible = isUserLoggedInSuccessfullyAlertDialogVisible,
            onDismissRequest = {
                isUserLoggedInSuccessfullyAlertDialogVisible = false
            }
        )
        UserLoggedInFailureAlertDialog(
            email = email,
            visible = isUserLoggedInFailureAlertDialogVisible,
            onDismissRequest = {
                isUserLoggedInFailureAlertDialogVisible = false
            }
        )
    }

    @Composable
    private fun FunctionNotAvailableAlertDialog(
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = "Esta función no está disponible",
            message = "Nosotros te avisaremos cuando lo esté. Mientras puedes seguir disfrutando de las demás función que tenemos para ti.",
            confirmText = "Gracias",
            visible, onDismissRequest
        )
    }

    @Composable
    private fun UserLoggedInSuccessfullyAlertDialog(
        email: String,
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = "¡Bienvenido!",
            message = "Tu contraseña coincide con tu correo $email. Puedes disfrutar de todas nuestras funcionalidades.",
            confirmText = "¡Excelente!",
            visible,
            onDismissRequest
        )
    }

    @Composable
    private fun UserLoggedInFailureAlertDialog(
        email: String,
        visible: Boolean = false,
        onDismissRequest: () -> Unit = {}
    ) {
        SimpleAlertDialog(
            title = "Tu contraseña es incorrecta",
            message = "Intenta volverla a ingresar",
            confirmText = "Volver a intentarlo",
            visible,
            onDismissRequest
        )
    }

    @Composable
    private fun SimpleAlertDialog(
        title: String,
        message: String,
        confirmText: String,
        visible: Boolean,
        onDismissRequest: () -> Unit
    ) {
        AnimatedVisibility(visible = visible) {
            AlertDialog(
                title = {
                    Text(text = title)
                },
                text = {
                    Text(text = message)
                },
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = confirmText)
                    }
                },
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    Navigator(screen = LoginScreen("email"))
}
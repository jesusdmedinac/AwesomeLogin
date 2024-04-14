package com.jesusdmedinac.baubap.awesomelogin.home.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jesusdmedinac.baubap.awesomelogin.R
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens.LoginScreen

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var emailTextFieldValue by remember {
            mutableStateOf(TextFieldValue(""))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Ingresa tu correo para empezar",
                style = MaterialTheme.typography.titleLarge
            )
            TextField(
                value = emailTextFieldValue,
                onValueChange = {
                    emailTextFieldValue = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigator.push(LoginScreen) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.start))
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Navigator(screen = HomeScreen())
}
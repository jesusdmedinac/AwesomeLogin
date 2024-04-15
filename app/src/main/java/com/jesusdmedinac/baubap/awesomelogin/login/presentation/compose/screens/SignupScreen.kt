package com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class LoginScreen(
    val email: String,
) : Screen {
    @Composable
    override fun Content() {
        Text(text = "LoginScreen")
    }
}
package com.jesusdmedinac.baubap.awesomelogin.signup.presentation.compose.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class SignupScreen(
    private val email: String,
) : Screen {
    @Composable
    override fun Content() {
        Text(text = "SignupScreen")
    }
}
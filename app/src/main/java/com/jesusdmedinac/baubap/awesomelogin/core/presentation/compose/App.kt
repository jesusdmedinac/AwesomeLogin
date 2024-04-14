package com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens.LoginScreen
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.ui.theme.AwesomeLoginTheme
import com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose.SplashScreen

@Composable
fun App() {
    AwesomeLoginTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigator(SplashScreen)
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
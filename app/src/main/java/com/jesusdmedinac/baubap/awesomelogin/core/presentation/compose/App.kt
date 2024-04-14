package com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.ui.theme.AwesomeLoginTheme
import com.jesusdmedinac.baubap.awesomelogin.home.HomeModule
import com.jesusdmedinac.baubap.awesomelogin.splash.SplashModule
import com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose.SplashScreen
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module

@Composable
fun App() {
    AwesomeLoginTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            KoinApplication(application = {
                modules(
                    HomeModule().module,
                    SplashModule().module,
                )
            }) {
                Navigator(SplashScreen())
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
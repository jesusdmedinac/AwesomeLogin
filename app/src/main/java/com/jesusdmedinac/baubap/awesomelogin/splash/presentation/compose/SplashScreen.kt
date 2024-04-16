package com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.main.presentation.compose.HomeScreen
import com.jesusdmedinac.baubap.awesomelogin.splash.SplashModule
import com.jesusdmedinac.baubap.awesomelogin.splash.presentation.model.SplashScreenModel
import com.jesusdmedinac.baubap.awesomelogin.splash.presentation.model.SplashScreenSideEffect
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel: SplashScreenModel = getScreenModel()
        val sideEffect: SplashScreenSideEffect by screenModel
            .container
            .sideEffectFlow
            .collectAsState(initial = SplashScreenSideEffect.Idle)

        LaunchedEffect(sideEffect) {
            if (sideEffect is SplashScreenSideEffect.NavigateToHome) {
                navigator.push(HomeScreen())
            }
        }
        LaunchedEffect(Unit) {
            screenModel.navigateToHome()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "SplashScreen")
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    KoinApplication(application = {
        modules(
            CoreModule().module,
            SplashModule().module,
        )
    }) {
        Navigator(screen = SplashScreen())
    }
}
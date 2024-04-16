package com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScreenTransition
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.ui.theme.AwesomeLoginTheme
import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
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
                    CoreModule().module,
                    MainModule().module,
                    LoginModule().module,
                    SignupModule().module,
                    SplashModule().module,
                )
            }) {
                Navigator(SplashScreen()) { navigator ->
                    val animationSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow)
                    ScreenTransition(
                        navigator = navigator,
                        transition = {
                            scaleIn(animationSpec = animationSpec) togetherWith scaleOut(
                                animationSpec = animationSpec
                            )
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AppPreview() {
    App()
}
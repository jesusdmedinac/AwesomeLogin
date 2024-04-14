package com.jesusdmedinac.baubap.awesomelogin.splash.presentation.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import org.koin.core.annotation.Single
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

@Single
class SplashScreenModel : ScreenModel, ContainerHost<SplashScreenState, SplashScreenSideEffect> {
    fun navigateToHome() = intent {
        delay(3000)
        postSideEffect(SplashScreenSideEffect.NavigateToHome)
        delay(100)
        postSideEffect(SplashScreenSideEffect.Idle)
    }

    override val container: Container<SplashScreenState, SplashScreenSideEffect> =
        screenModelScope.container(SplashScreenState())
}

class SplashScreenState

sealed class SplashScreenSideEffect {
    data object Idle : SplashScreenSideEffect()
    data object NavigateToHome : SplashScreenSideEffect()
}
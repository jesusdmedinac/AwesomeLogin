package com.jesusdmedinac.baubap.awesomelogin.splash.presentation.model

import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.orbitmvi.orbit.test.test

class SplashScreenModelTest {
    @Test
    fun `navigateToHome should post NavigateToHome after 3000 ms delay and then post Idle after 100 ms delay`() =
        runTest {
            // given
            SplashScreenModel().test(this, SplashScreenState()) {
                expectInitialState()
                containerHost.navigateToHome()
                expectSideEffect(SplashScreenSideEffect.NavigateToHome)
                expectSideEffect(SplashScreenSideEffect.Idle)
            }
        }
}
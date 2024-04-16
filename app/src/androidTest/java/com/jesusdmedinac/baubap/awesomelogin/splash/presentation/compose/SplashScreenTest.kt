package com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import com.jesusdmedinac.baubap.awesomelogin.splash.SplashModule
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest

class SplashScreenTest : KoinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun type_your_email_to_startShouldBeDisplayedGivenSplashScreenIsDisplayedAfter3000ms() =
        runTest {
            // given
            composeTestRule.setContent {
                KoinApplication(application = {
                    modules(
                        CoreModule().module,
                        MainModule().module,
                        LoginModule().module,
                        SignupModule().module,
                        SplashModule().module,
                    )
                }) {
                    Navigator(screen = SplashScreen())
                }
            }

            // when
            Thread.sleep(3001)

            // then
            composeTestRule
                .onNodeWithTag("type_your_email_to_start")
                .assertIsDisplayed()

            // tear down
            stopKoin()
        }
}
package com.jesusdmedinac.baubap.awesomelogin.main.presentation.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import com.jesusdmedinac.baubap.awesomelogin.splash.SplashModule
import com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose.SplashScreen
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest

class MainScreenTest : KoinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            KoinApplication(application = {
                modules(
                    CoreModule().module,
                    MainModule().module,
                    LoginModule().module,
                    SignupModule().module,
                )
            }) {
                Navigator(screen = MainScreen())
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun start_button_clickShouldDisplay_login_screen_title_given_email_text_field_isPerformedWithTextInputAsExistingEmail() =
        runTest {
            // given
            composeTestRule
                .onNodeWithTag("email_text_field")
                .performTextInput("mail@co.co")

            // when
            composeTestRule
                .onNodeWithTag("start_button")
                .performClick()

            // then
            composeTestRule
                .onNodeWithTag("login_screen_title")
                .assertIsDisplayed()
        }

    @Test
    fun start_button_clickShouldDisplay_login_screen_title_given_email_text_field_isPerformedWithTextInputAsNonExistingEmail() =
        runTest {
            // given
            composeTestRule
                .onNodeWithTag("email_text_field")
                .performTextInput("non-existing@co.co")

            // when
            composeTestRule
                .onNodeWithTag("start_button")
                .performClick()

            // then
            composeTestRule
                .onNodeWithTag("signup_screen_title")
                .assertIsDisplayed()
        }
}
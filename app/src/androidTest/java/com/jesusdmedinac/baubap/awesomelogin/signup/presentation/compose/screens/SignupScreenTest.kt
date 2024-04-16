package com.jesusdmedinac.baubap.awesomelogin.signup.presentation.compose.screens

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens.LoginScreen
import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module

class SignupScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun signup_button_shouldDisplay_simple_alert_dialog_title_withTextThatContains_Bienvenido_given_password_text_field_isPerformedWithValidPassword() {
        // given
        val email = "mail@co.co"
        composeTestRule.setContent {
            KoinApplication(application = {
                modules(
                    CoreModule().module,
                    SignupModule().module,
                )
            }) {
                Navigator(screen = SignupScreen(email))
            }
        }
        val password = "1234"
        composeTestRule
            .onNodeWithTag("password_text_field")
            .performTextInput(password)

        // when
        composeTestRule
            .onNodeWithTag("signup_button")
            .performClick()

        // then
        composeTestRule
            .onNodeWithTag("simple_alert_dialog_title")
            .assertTextContains("¡Bienvenido!")

        // tear down
        stopKoin()
    }
}
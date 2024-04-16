package com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import kotlin.random.Random

class LoginScreenTest : KoinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun login_button_clickShouldDisplay_simple_alert_dialog_title_withTextThatContains_Bienvenido_given_password_text_field_isPerformedWithValidPassword() {
        // given
        val email = "mail@co.co"
        composeTestRule.setContent {
            KoinApplication(application = {
                modules(
                    CoreModule().module,
                    LoginModule().module,
                )
            }) {
                Navigator(screen = LoginScreen(email))
            }
        }
        val password = "1234"
        composeTestRule
            .onNodeWithTag("password_text_field")
            .performTextInput(password)

        // when
        composeTestRule
            .onNodeWithTag("login_button")
            .performClick()

        // then
        composeTestRule
            .onNodeWithTag("simple_alert_dialog_title")
            .assertTextContains("¡Bienvenido!")

        // tear down
        stopKoin()
    }

    @Test
    fun login_button_clickShouldDisplay_simple_alert_dialog_title_withTextThatContains_Tu_contraseña_es_incorrecta_given_password_text_field_isPerformedWithInvalidPassword() {
        // given
        val email = "mail@co.co"
        composeTestRule.setContent {
            KoinApplication(application = {
                modules(
                    CoreModule().module,
                    LoginModule().module,
                )
            }) {
                Navigator(screen = LoginScreen(email))
            }
        }
        val password = Random.nextInt().toString()
        composeTestRule
            .onNodeWithTag("password_text_field")
            .performTextInput(password)

        // when
        composeTestRule
            .onNodeWithTag("login_button")
            .performClick()

        // then
        composeTestRule
            .onNodeWithTag("simple_alert_dialog_title")
            .assertTextContains("Tu contraseña es incorrecta")

        // tear down
        stopKoin()
    }
}
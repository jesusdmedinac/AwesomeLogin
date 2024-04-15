package com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.model

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.login.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.orbitmvi.orbit.test.test
import kotlin.random.Random

class LoginScreenModelTest : KoinTest {
    private lateinit var loginUseCase: LoginUseCase

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(LoginModule().module)
        }
        loginUseCase = declareMock()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `onPasswordChange should reduce state with password given password`() = runTest {
        // given
        LoginScreenModel(loginUseCase).test(this, LoginScreenState()) {
            expectInitialState()

            // when
            val password = Random.nextInt().toString()
            containerHost.onPasswordChange(password)

            // then
            expectState { copy(password = password) }
        }
    }

    @Test
    fun `onLoginClick should post UserLoggedInSuccessfully given loginUseCase returns result as success`() =
        runTest {
            // given
            val email = "Random.nextInt().toString()"
            val password = "Random.nextInt().toString()"
            val userCredentials = UserCredentials(email, password)
            val user: User = mockk()
            coEvery { loginUseCase(userCredentials) } returns Result.success(user)
            LoginScreenModel(loginUseCase).test(this, LoginScreenState(password)) {
                expectInitialState()

                // when
                containerHost.onLoginClick(email)

                // then
                expectSideEffect(LoginScreenSideEffect.UserLoggedInSuccessfully(user))
            }
        }
}
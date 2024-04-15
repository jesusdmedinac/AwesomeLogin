package com.jesusdmedinac.baubap.awesomelogin.signup.presentation.model

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import com.jesusdmedinac.baubap.awesomelogin.signup.domain.usecase.SignupUseCase
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
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.orbitmvi.orbit.test.test
import kotlin.random.Random

class SignupScreenModelTest : KoinTest {

    private lateinit var signupUseCase: SignupUseCase

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(SignupModule().module)
        }
        signupUseCase = declareMock()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `onPasswordChange should reduce state with password given password`() = runTest {
        // given
        SignupScreenModel(signupUseCase).test(this, SignupScreenState()) {
            expectInitialState()

            // when
            val password = Random.nextInt().toString()
            containerHost.onPasswordChange(password)

            // then
            expectState { copy(password = password) }
        }
    }

    @Test
    fun `onSignupClick should post UserLoggedInSuccessfully given signupUseCase returns result as success`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val password = Random.nextInt().toString()
            val userCredentials = UserCredentials(email, password)
            val user: User = mockk()
            coEvery { signupUseCase(userCredentials) } returns Result.success(user)
            SignupScreenModel(signupUseCase).test(this, SignupScreenState(password)) {
                expectInitialState()

                // when
                containerHost.onSignupClick(email)

                // then
                expectSideEffect(SignupScreenSideEffect.UserSignedUpSuccessfully(user))
            }
        }

    @Test
    fun `onSignupClick should post UserLoggedInFailure given signupUseCase returns result as failure`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val password = Random.nextInt().toString()
            val userCredentials = UserCredentials(email, password)
            coEvery { signupUseCase(userCredentials) } returns Result.failure(Throwable())
            SignupScreenModel(signupUseCase).test(this, SignupScreenState(password)) {
                expectInitialState()

                // when
                containerHost.onSignupClick(email)

                // then
                expectSideEffect(SignupScreenSideEffect.UserSignedUpFailure(email))
            }
        }
}
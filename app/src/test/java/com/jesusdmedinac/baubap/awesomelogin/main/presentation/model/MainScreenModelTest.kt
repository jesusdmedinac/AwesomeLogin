package com.jesusdmedinac.baubap.awesomelogin.main.presentation.model

import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.main.domain.usecase.CheckAccountExistenceAndAuthenticationUseCase
import io.mockk.coEvery
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

class MainScreenModelTest : KoinTest {
    private lateinit var checkAccountExistenceAndAuthenticationUseCase: CheckAccountExistenceAndAuthenticationUseCase

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(MainModule().module)
        }
        checkAccountExistenceAndAuthenticationUseCase = declareMock()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `onEmailChange should reduce state with email given email`() = runTest {
        // given
        HomeScreenModel(checkAccountExistenceAndAuthenticationUseCase).test(
            this,
            HomeScreenState()
        ) {
            expectInitialState()

            // when
            val email = Random.nextInt().toString()
            containerHost.onEmailChange(email)

            // then
            expectState { copy(email = email) }
        }
    }

    @Test
    fun `onStartClick should post NavigateToLogin given checkAccountExistenceAndAuthenticationUseCase returns result with true`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            coEvery { checkAccountExistenceAndAuthenticationUseCase(email) } returns Result
                .success(true)
            HomeScreenModel(checkAccountExistenceAndAuthenticationUseCase).test(
                this,
                HomeScreenState(email)
            ) {
                expectInitialState()

                // when
                containerHost.onStartClick()

                // then
                expectSideEffect(HomeScreenSideEffect.NavigateToLogin(email))
            }
        }

    @Test
    fun `onStartClick should post NavigateToSignup given checkAccountExistenceAndAuthenticationUseCase returns result with false`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            coEvery { checkAccountExistenceAndAuthenticationUseCase(email) } returns Result
                .success(false)
            HomeScreenModel(checkAccountExistenceAndAuthenticationUseCase).test(
                this,
                HomeScreenState(email)
            ) {
                expectInitialState()

                // when
                containerHost.onStartClick()

                // then
                expectSideEffect(HomeScreenSideEffect.NavigateToSignup(email))
            }
        }

    @Test
    fun `onStartClick should post NavigateToSignup given checkAccountExistenceAndAuthenticationUseCase returns result as failure`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            coEvery { checkAccountExistenceAndAuthenticationUseCase(email) } returns Result
                .failure(Throwable())
            HomeScreenModel(checkAccountExistenceAndAuthenticationUseCase).test(
                this,
                HomeScreenState(email)
            ) {
                expectInitialState()

                // when
                containerHost.onStartClick()

                // then
                expectSideEffect(HomeScreenSideEffect.NavigateToSignup(email))
            }
        }
}
package com.jesusdmedinac.baubap.awesomelogin.home.presentation.model

import com.jesusdmedinac.baubap.awesomelogin.home.domain.usecase.CheckAccountExistenceAndAuthenticationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.orbitmvi.orbit.test.test
import kotlin.random.Random

class HomeScreenModelTest : KoinTest {
    private val checkAccountExistenceAndAuthenticationUseCase: CheckAccountExistenceAndAuthenticationUseCase by inject()

    @Before
    fun setUp() {
        startKoin {
            modules()
        }
    }

    @Test
    fun `onEmailChange should reduce state with email given email`() = runTest {
        // given
        HomeScreenModel(checkAccountExistenceAndAuthenticationUseCase).test(this, HomeScreenState()) {
            expectInitialState()

            // when
            val email = Random.nextInt().toString()
            containerHost.onEmailChange(email)

            // then
            expectState { copy(email = email) }
        }
    }
}
package com.jesusdmedinac.baubap.awesomelogin.signup.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.home.domain.repository.UserRepository
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import com.jesusdmedinac.baubap.awesomelogin.login.domain.usecase.LoginUseCaseImpl
import com.jesusdmedinac.baubap.awesomelogin.signup.SignupModule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import kotlin.random.Random

class SignupUseCaseImplTest : KoinTest {
    private lateinit var userRepository: UserRepository

    private lateinit var signupUseCaseImpl: SignupUseCaseImpl

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(SignupModule().module)
        }
        userRepository = declareMock()
        signupUseCaseImpl = get()
    }

    @Test
    fun `invoke should return result given login on userRepository returns result with userCredentials`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val password = Random.nextInt().toString()
            val userCredentials = UserCredentials(email, password)
            val user: User = mockk()
            coEvery { userRepository.signup(userCredentials) } returns Result.success(user)

            // when
            val result = signupUseCaseImpl(userCredentials)
                .getOrNull()

            // then
            assertThat(result, `is`(user))
        }
}
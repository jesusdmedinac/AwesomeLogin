package com.jesusdmedinac.baubap.awesomelogin.login.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.home.domain.repository.UserRepository
import com.jesusdmedinac.baubap.awesomelogin.login.LoginModule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
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

class LoginUseCaseImplTest : KoinTest {
    private lateinit var userRepository: UserRepository

    private lateinit var loginUseCaseImpl: LoginUseCaseImpl

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(LoginModule().module)
        }
        userRepository = declareMock()
        loginUseCaseImpl = get()
    }

    @Test
    fun `invoke should return result given login on userRepository returns result with userCredentials`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val password = Random.nextInt().toString()
            val userCredentials = UserCredentials(email, password)
            val user: User = mockk()
            coEvery { userRepository.login(userCredentials) } returns Result.success(user)

            // when
            val result = loginUseCaseImpl(userCredentials)
                .getOrNull()

            // then
            assertThat(result, `is`(user))
        }
}
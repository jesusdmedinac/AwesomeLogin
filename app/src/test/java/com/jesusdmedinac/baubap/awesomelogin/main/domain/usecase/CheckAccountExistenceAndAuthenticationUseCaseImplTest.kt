package com.jesusdmedinac.baubap.awesomelogin.main.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.main.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import kotlin.random.Random

class CheckAccountExistenceAndAuthenticationUseCaseImplTest : KoinTest {
    private lateinit var userRepository: UserRepository

    private lateinit var checkAccountExistenceAndAuthenticationUseCaseImpl: CheckAccountExistenceAndAuthenticationUseCaseImpl

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(
                MainModule().module
            )
        }
        userRepository = declareMock<UserRepository>()
        checkAccountExistenceAndAuthenticationUseCaseImpl = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `invoke should call checkAccountExistenceAndAuthentication on UserRepository with email`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val anyResult = listOf(
                Result.success(Random.nextBoolean()),
                Result.failure(Throwable())
            ).random()
            every { userRepository.checkAccountExistenceAndAuthentication(email) } returns anyResult

            // when
            val result = checkAccountExistenceAndAuthenticationUseCaseImpl(email)

            // then
            assertThat(result, `is`(anyResult))
        }
}
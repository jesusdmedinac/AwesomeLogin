package com.jesusdmedinac.baubap.awesomelogin.home.data.repository

import com.jesusdmedinac.baubap.awesomelogin.home.HomeModule
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.UserRemoteDataSource
import com.jesusdmedinac.baubap.awesomelogin.core.data.repository.UserRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.get
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import kotlin.random.Random

class UserRepositoryImplTest : KoinTest {
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(
                HomeModule().module
            )
        }
        userRemoteDataSource = declareMock<UserRemoteDataSource>()
        userRepositoryImpl = get()
    }

    @Test
    fun `checkAccountExistenceAndAuthentication should return result given checkAccountExistenceAndAuthentication on UserRemoteDataSource`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val anyResult = listOf(
                Result.success(Random.nextBoolean()),
                Result.failure(Throwable())
            ).random()
            every { userRemoteDataSource.checkAccountExistenceAndAuthentication(email) } returns anyResult

            // when
            val result = userRepositoryImpl.checkAccountExistenceAndAuthentication(email)

            // then
            assertThat(result, `is`(anyResult))
        }
}
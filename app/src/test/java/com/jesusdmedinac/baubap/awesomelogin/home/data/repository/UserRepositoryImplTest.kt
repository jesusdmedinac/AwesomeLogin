package com.jesusdmedinac.baubap.awesomelogin.home.data.repository

import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.RemoteUser
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.RemoteUserCredentials
import com.jesusdmedinac.baubap.awesomelogin.home.HomeModule
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.UserRemoteDataSource
import com.jesusdmedinac.baubap.awesomelogin.core.data.repository.UserRepositoryImpl
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.core.mapper.UserCredentialsToRemoteUserCredentialsMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import kotlin.random.Random

class UserRepositoryImplTest : KoinTest {
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private lateinit var userCredentialsToRemoteUserCredentialsMapper: UserCredentialsToRemoteUserCredentialsMapper

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Before
    fun setUp() {
        startKoin {
            modules(
                CoreModule().module
            )
        }
        userRemoteDataSource = declareMock()
        userCredentialsToRemoteUserCredentialsMapper = declareMock()
        userRepositoryImpl = get()
    }

    @After
    fun tearDown() {
        stopKoin()
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

    @Test
    fun `login should return result given login on userRemoteDataSource returns result with remoteUserCredentials`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val password = Random.nextInt().toString()
            val userCredentials = UserCredentials(email, password)
            val remoteUserCredentials = mockk<RemoteUserCredentials>()
            every { userCredentialsToRemoteUserCredentialsMapper.map(userCredentials) } returns remoteUserCredentials
            coEvery { userRemoteDataSource.login(remoteUserCredentials) } returns Result
                .success(RemoteUser(email))

            // when
            val result = userRepositoryImpl.login(userCredentials)
                .getOrNull()

            // then
            assertThat(result, `is`(User(email)))
        }

    @Test
    fun `signup should return result given signup on userRemoteDataSource returns result with remoteUserCredentials`() =
        runTest {
            // given
            val email = Random.nextInt().toString()
            val password = Random.nextInt().toString()
            val userCredentials = UserCredentials(email, password)
            val remoteUserCredentials = mockk<RemoteUserCredentials>()
            every { userCredentialsToRemoteUserCredentialsMapper.map(userCredentials) } returns remoteUserCredentials
            coEvery { userRemoteDataSource.signup(remoteUserCredentials) } returns Result
                .success(RemoteUser(email))

            // when
            val result = userRepositoryImpl.signup(userCredentials)
                .getOrNull()

            // then
            assertThat(result, `is`(User(email)))
        }
}
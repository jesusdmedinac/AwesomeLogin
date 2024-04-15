package com.jesusdmedinac.baubap.awesomelogin.home.data.remote

import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.RemoteUser
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.RemoteUserCredentials
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.UserRemoteDataSourceImpl
import com.jesusdmedinac.baubap.awesomelogin.home.HomeModule
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
import kotlin.random.Random

class UserRemoteDataSourceImplTest : KoinTest {
    private lateinit var userRemoteDataSourceImpl: UserRemoteDataSourceImpl

    private val availableUserCredentials = mutableListOf(
        RemoteUserCredentials("mail@co.co", "1234"),
        RemoteUserCredentials("some@co.co", "4321"),
        RemoteUserCredentials("any@co.co", "0987"),
    )

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
        userRemoteDataSourceImpl = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `checkAccountExistenceAndAuthentication should return a boolean given email exists`() =
        runTest {
            // given
            val email = listOf(
                Random.nextInt().toString(),
                availableUserCredentials.random().email
            )
                .random()

            // when
            val result = userRemoteDataSourceImpl.checkAccountExistenceAndAuthentication(email)
                .getOrNull()

            // then
            assertThat(result, `is`(email in availableUserCredentials.map { it.email }))
        }

    @Test
    fun `login should return RemoteUser with email given RemoteUserCredentials exists`() = runTest {
        // given
        val remoteUserCredentials = availableUserCredentials.random()

        // when
        val result = userRemoteDataSourceImpl.login(remoteUserCredentials)
            .getOrNull()

        // then
        assertThat(result, `is`(RemoteUser(remoteUserCredentials.email)))
    }

    @Test
    fun `login should return RemoteUser with email given signup is called with RemoteUserCredentials`() = runTest {
        // given
        val remoteUserCredentials = availableUserCredentials.random()
        userRemoteDataSourceImpl.signup(remoteUserCredentials)

        // when
        val result = userRemoteDataSourceImpl.login(remoteUserCredentials)
            .getOrNull()

        // then
        assertThat(result, `is`(RemoteUser(remoteUserCredentials.email)))
    }
}
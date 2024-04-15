package com.jesusdmedinac.baubap.awesomelogin.home.data.remote

import com.jesusdmedinac.baubap.awesomelogin.home.HomeModule
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
import kotlin.random.Random

class UserRemoteDataSourceImplTest : KoinTest {
    private lateinit var userRemoteDataSourceImpl: UserRemoteDataSourceImpl


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
        userRemoteDataSourceImpl = get()
    }

    @Test
    fun `checkAccountExistenceAndAuthentication should return a boolean given email exists`() =
        runTest {
            // given
            val availableEmails = listOf(
                "mail@co.co",
                "some@co.co",
                "any@co.co",
            )
            val email = listOf(
                Random.nextInt().toString(),
                availableEmails.random()
            )
                .random()

            // when
            val result = userRemoteDataSourceImpl.checkAccountExistenceAndAuthentication(email)
                .getOrNull()

            // then
            assertThat(result, `is`(email in availableEmails))
        }
}
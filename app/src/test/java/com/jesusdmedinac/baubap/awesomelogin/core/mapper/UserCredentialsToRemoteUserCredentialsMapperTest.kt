package com.jesusdmedinac.baubap.awesomelogin.core.mapper

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UserCredentialsToRemoteUserCredentialsMapperTest {
    private lateinit var userCredentialsToRemoteUserCredentialsMapper: UserCredentialsToRemoteUserCredentialsMapper

    @Before
    fun setUp() {
        userCredentialsToRemoteUserCredentialsMapper =
            UserCredentialsToRemoteUserCredentialsMapper()
    }

    @Test
    fun `map should return RemoteUserCredentials with email given input has email`() {
        // given
        val email = Random.nextInt().toString()
        val userCredentials = UserCredentials(email, "password")

        // when
        val result = userCredentialsToRemoteUserCredentialsMapper.map(userCredentials)
            .email

        // then
        assertThat(result, `is`(email))
    }

    @Test
    fun `map should return RemoteUserCredentials with password given input has password`() {
        // given
        val password = Random.nextInt().toString()
        val userCredentials = UserCredentials("email", password)

        // when
        val result = userCredentialsToRemoteUserCredentialsMapper.map(userCredentials)
            .password

        // then
        assertThat(result, `is`(password))
    }
}
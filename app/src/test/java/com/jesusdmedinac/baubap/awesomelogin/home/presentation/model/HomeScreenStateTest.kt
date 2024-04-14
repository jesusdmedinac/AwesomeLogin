package com.jesusdmedinac.baubap.awesomelogin.home.presentation.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.random.Random

class HomeScreenStateTest {
    @Test
    fun `isValidEmail should return false given email is not valid`() {
        // given
        val email = Random.nextInt().toString()
        val homeScreenState = HomeScreenState(email = email)

        // when
        val result = homeScreenState.isValidEmail

        // then
        assertThat(result, `is`(false))
    }

    @Test
    fun `isValidEmail should return true given email is valid`() {
        // given
        val email = "a@aa.aa"
        val homeScreenState = HomeScreenState(email = email)

        // when
        val result = homeScreenState.isValidEmail

        // then
        assertThat(result, `is`(true))
    }

    @Test
    fun `hasAt should return false given email does not contain @`() {
        // given
        val email = Random.nextInt().toString()
        val homeScreenState = HomeScreenState(email = email)

        // when
        val result = homeScreenState.hasAt

        // then
        assertThat(result, `is`(false))
    }

    @Test
    fun `hasAt should return true given email contains @`() {
        // given
        val email = "${Random.nextInt()}@${Random.nextInt()}"
        val homeScreenState = HomeScreenState(email = email)

        // when
        val result = homeScreenState.hasAt

        // then
        assertThat(result, `is`(true))
    }
}
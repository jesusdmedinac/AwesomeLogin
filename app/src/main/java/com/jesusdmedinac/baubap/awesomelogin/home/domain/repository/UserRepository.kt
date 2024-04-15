package com.jesusdmedinac.baubap.awesomelogin.home.domain.repository

interface UserRepository {
    fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean>
}
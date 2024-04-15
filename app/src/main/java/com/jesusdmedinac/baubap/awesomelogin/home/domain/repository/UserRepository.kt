package com.jesusdmedinac.baubap.awesomelogin.home.domain.repository

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials

interface UserRepository {
    fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean>
    fun login(userCredentials: UserCredentials): Result<User>
}
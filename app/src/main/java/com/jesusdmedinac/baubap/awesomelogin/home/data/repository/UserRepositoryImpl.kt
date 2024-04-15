package com.jesusdmedinac.baubap.awesomelogin.home.data.repository

import com.jesusdmedinac.baubap.awesomelogin.home.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class UserRepositoryImpl : UserRepository {
    override fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean> {
        TODO("Not yet implemented")
    }
}
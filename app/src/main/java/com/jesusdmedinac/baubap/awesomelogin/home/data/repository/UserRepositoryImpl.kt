package com.jesusdmedinac.baubap.awesomelogin.home.data.repository

import com.jesusdmedinac.baubap.awesomelogin.home.data.remote.UserRemoteDataSource
import com.jesusdmedinac.baubap.awesomelogin.home.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean> =
        userRemoteDataSource.checkAccountExistenceAndAuthentication(email)
}
package com.jesusdmedinac.baubap.awesomelogin.core.data.repository

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.UserRemoteDataSource
import com.jesusdmedinac.baubap.awesomelogin.home.domain.repository.UserRepository
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.core.mapper.UserCredentialsToRemoteUserCredentialsMapper
import org.koin.core.annotation.Single

@Single
class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userCredentialsToRemoteUserCredentialsMapper: UserCredentialsToRemoteUserCredentialsMapper
) : UserRepository {
    override fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean> =
        userRemoteDataSource.checkAccountExistenceAndAuthentication(email)

    override fun login(userCredentials: UserCredentials): Result<User> {
        val remoteUserCredentials = userCredentialsToRemoteUserCredentialsMapper.map(userCredentials)
        return userRemoteDataSource.login(remoteUserCredentials)
            .fold(
                onSuccess = { Result.success(User(it.email))},
                onFailure = { Result.failure(it) }
            )
    }
}
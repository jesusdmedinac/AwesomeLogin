package com.jesusdmedinac.baubap.awesomelogin.core.data.remote

import org.koin.core.annotation.Single

interface UserRemoteDataSource {
    fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean>
    fun login(remoteUserCredentials: RemoteUserCredentials): Result<RemoteUser>
    fun signup(remoteUserCredentials: RemoteUserCredentials): Result<RemoteUser>
}

@Single
class UserRemoteDataSourceImpl : UserRemoteDataSource {
    private val availableUserCredentials = mutableListOf(
        RemoteUserCredentials("mail@co.co", "1234"),
        RemoteUserCredentials("some@co.co", "4321"),
        RemoteUserCredentials("any@co.co", "0987"),
    )

    override fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean> =
        runCatching { email in availableUserCredentials.map { it.email } }

    override fun login(remoteUserCredentials: RemoteUserCredentials): Result<RemoteUser> =
        runCatching {
            availableUserCredentials
                .first { it == remoteUserCredentials }
                .let { RemoteUser(it.email) }
        }

    override fun signup(remoteUserCredentials: RemoteUserCredentials): Result<RemoteUser> =
        runCatching {
            if (remoteUserCredentials !in availableUserCredentials)
                availableUserCredentials.add(remoteUserCredentials)
            RemoteUser(remoteUserCredentials.email)
        }
}

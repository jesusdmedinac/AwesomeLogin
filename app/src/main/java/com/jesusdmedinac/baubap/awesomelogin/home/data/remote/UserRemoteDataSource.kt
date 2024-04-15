package com.jesusdmedinac.baubap.awesomelogin.home.data.remote

import org.koin.core.annotation.Single

interface UserRemoteDataSource {
    fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean>
}

@Single
class UserRemoteDataSourceImpl : UserRemoteDataSource {
    private val availableEmails = listOf(
        "mail@co.co",
        "some@co.co",
        "any@co.co",
    )

    override fun checkAccountExistenceAndAuthentication(email: String): Result<Boolean> =
        runCatching { email in availableEmails }
}

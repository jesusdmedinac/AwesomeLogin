package com.jesusdmedinac.baubap.awesomelogin.home.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.home.domain.model.User
import org.koin.core.annotation.Single

interface CheckAccountExistenceAndAuthenticationUseCase {
    suspend operator fun invoke(email: String): Result<User>
}

@Single
class CheckAccountExistenceAndAuthenticationUseCaseImpl : CheckAccountExistenceAndAuthenticationUseCase {
    override suspend fun invoke(email: String): Result<User> {
        TODO("Not yet implemented")
    }
}
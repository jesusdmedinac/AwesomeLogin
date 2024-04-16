package com.jesusdmedinac.baubap.awesomelogin.main.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.main.domain.repository.UserRepository
import org.koin.core.annotation.Single

interface CheckAccountExistenceAndAuthenticationUseCase {
    suspend operator fun invoke(email: String): Result<Boolean>
}

@Single
class CheckAccountExistenceAndAuthenticationUseCaseImpl(
    private val userRepository: UserRepository
) : CheckAccountExistenceAndAuthenticationUseCase {
    override suspend fun invoke(email: String): Result<Boolean> =
        userRepository.checkAccountExistenceAndAuthentication(email)
}
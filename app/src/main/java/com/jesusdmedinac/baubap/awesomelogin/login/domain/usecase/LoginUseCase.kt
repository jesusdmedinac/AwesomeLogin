package com.jesusdmedinac.baubap.awesomelogin.login.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.main.domain.repository.UserRepository
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import org.koin.core.annotation.Single

interface LoginUseCase {
    suspend operator fun invoke(userCredentials: UserCredentials): Result<User>
}

@Single
class LoginUseCaseImpl(
    private val userRepository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(userCredentials: UserCredentials): Result<User> =
        userRepository.login(userCredentials)
}
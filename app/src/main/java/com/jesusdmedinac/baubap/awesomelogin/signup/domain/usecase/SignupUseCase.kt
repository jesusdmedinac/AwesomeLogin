package com.jesusdmedinac.baubap.awesomelogin.signup.domain.usecase

import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.main.domain.repository.UserRepository
import org.koin.core.annotation.Single

interface SignupUseCase {
    suspend operator fun invoke(userCredentials: UserCredentials): Result<User>
}

@Single
class SignupUseCaseImpl(
    private val userRepository: UserRepository
) : SignupUseCase {
    override suspend fun invoke(userCredentials: UserCredentials): Result<User> =
        userRepository.signup(userCredentials)
}
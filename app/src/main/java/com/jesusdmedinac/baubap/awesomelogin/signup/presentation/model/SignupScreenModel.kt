package com.jesusdmedinac.baubap.awesomelogin.signup.presentation.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.signup.domain.usecase.SignupUseCase
import org.koin.core.annotation.Single
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@Single
class SignupScreenModel(
    private val signupUseCase: SignupUseCase
) : ScreenModel, ContainerHost<SignupScreenState, SignupScreenSideEffect> {
    override val container: Container<SignupScreenState, SignupScreenSideEffect> =
        screenModelScope.container(SignupScreenState())

    fun onPasswordChange(password: String) = intent {
        reduce { state.copy(password = password) }
    }

    fun onSignupClick(email: String) = intent {
        val password = state.password
        val userCredentials = UserCredentials(email, password)
        val sideEffect = signupUseCase(userCredentials)
            .fold(
                onSuccess = { user ->
                    SignupScreenSideEffect.UserSignedUpSuccessfully(user)
                },
                onFailure = {
                    SignupScreenSideEffect.UserSignedUpFailure(email)
                }
            )
        postSideEffect(sideEffect)
    }
}

data class SignupScreenState(
    val password: String = ""
)

sealed class SignupScreenSideEffect{
    data object Idle : SignupScreenSideEffect()
    data class UserSignedUpSuccessfully(val user: User) : SignupScreenSideEffect()
    data class UserSignedUpFailure(val email: String) : SignupScreenSideEffect()
}
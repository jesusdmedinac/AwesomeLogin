package com.jesusdmedinac.baubap.awesomelogin.login.presentation.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.User
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import com.jesusdmedinac.baubap.awesomelogin.login.domain.usecase.LoginUseCase
import org.koin.core.annotation.Single
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@Single
class LoginScreenModel(
    private val loginUseCase: LoginUseCase,
) : ScreenModel, ContainerHost<LoginScreenState, LoginScreenSideEffect> {
    override val container: Container<LoginScreenState, LoginScreenSideEffect> =
        screenModelScope.container(LoginScreenState())

    fun onPasswordChange(password: String) = intent {
        reduce { state.copy(password = password) }
    }

    fun onLoginClick(email: String) = intent {
        val password = state.password
        val userCredentials = UserCredentials(email, password)
        val sideEffect = loginUseCase(userCredentials)
            .fold(
                onSuccess = { user ->
                    LoginScreenSideEffect.UserLoggedInSuccessfully(user)
                },
                onFailure = {
                    LoginScreenSideEffect.UserLoggedInFailure(email)
                }
            )
        postSideEffect(sideEffect)
    }
}

data class LoginScreenState(
    val password: String = ""
)

sealed class LoginScreenSideEffect {
    data object Idle : LoginScreenSideEffect()
    data class UserLoggedInSuccessfully(val user: User) : LoginScreenSideEffect()
    data class UserLoggedInFailure(val email: String) : LoginScreenSideEffect()
}
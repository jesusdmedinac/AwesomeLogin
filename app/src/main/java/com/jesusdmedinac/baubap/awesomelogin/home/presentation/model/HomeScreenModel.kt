package com.jesusdmedinac.baubap.awesomelogin.home.presentation.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jesusdmedinac.baubap.awesomelogin.home.domain.usecase.CheckAccountExistenceAndAuthenticationUseCase
import org.koin.core.annotation.Single
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@Single
class HomeScreenModel(
    val checkAccountExistenceAndAuthenticationUseCase: CheckAccountExistenceAndAuthenticationUseCase
) : ScreenModel, ContainerHost<HomeScreenState, HomeScreenSideEffect> {
    override val container: Container<HomeScreenState, HomeScreenSideEffect> =
        screenModelScope.container(HomeScreenState())

    fun onEmailChange(email: String) = intent {
        reduce { state.copy(email = email) }
    }

    fun onStartClick() = intent {
        val sideEffect = runCatching { checkAccountExistenceAndAuthenticationUseCase(state.email) }
            .fold(
                onSuccess = { checkAccountResult ->
                    checkAccountResult
                        .fold(
                            onSuccess = { accountExists ->
                                if (accountExists)
                                    HomeScreenSideEffect.NavigateToLogin(state.email)
                                else
                                    HomeScreenSideEffect.NavigateToSignup(state.email)
                            },
                            onFailure = { HomeScreenSideEffect.NavigateToSignup(state.email) }
                        )
                },
                onFailure = { HomeScreenSideEffect.NavigateToSignup(state.email) }
            )
        postSideEffect(sideEffect)
    }
}

data class HomeScreenState(
    val email: String = "",
) {
    val isValidEmail: Boolean get() = email matches "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
    val hasAt: Boolean get() = "@" in email
}

sealed class HomeScreenSideEffect {
    data object Idle : HomeScreenSideEffect()
    data class NavigateToLogin(val email: String) : HomeScreenSideEffect()
    data class NavigateToSignup(val email: String) : HomeScreenSideEffect()
}
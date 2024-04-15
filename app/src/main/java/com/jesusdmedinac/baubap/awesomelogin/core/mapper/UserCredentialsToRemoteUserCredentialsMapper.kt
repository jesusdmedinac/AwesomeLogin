package com.jesusdmedinac.baubap.awesomelogin.core.mapper

import com.jesusdmedinac.baubap.awesomelogin.core.data.remote.RemoteUserCredentials
import com.jesusdmedinac.baubap.awesomelogin.core.domain.model.UserCredentials
import org.koin.core.annotation.Single

@Single
class UserCredentialsToRemoteUserCredentialsMapper {
    fun map(input: UserCredentials): RemoteUserCredentials = with(input) {
        RemoteUserCredentials(email, password)
    }
}
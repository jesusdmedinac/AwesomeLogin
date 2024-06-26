package com.jesusdmedinac.baubap.awesomelogin.main.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jesusdmedinac.baubap.awesomelogin.R
import com.jesusdmedinac.baubap.awesomelogin.core.CoreModule
import com.jesusdmedinac.baubap.awesomelogin.main.MainModule
import com.jesusdmedinac.baubap.awesomelogin.main.presentation.model.HomeScreenModel
import com.jesusdmedinac.baubap.awesomelogin.main.presentation.model.HomeScreenSideEffect
import com.jesusdmedinac.baubap.awesomelogin.login.presentation.compose.screens.LoginScreen
import com.jesusdmedinac.baubap.awesomelogin.signup.presentation.compose.screens.SignupScreen
import com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose.AwesomeAnimation
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel: HomeScreenModel = navigator.getNavigatorScreenModel()
        val screenState by screenModel
            .container
            .stateFlow
            .collectAsState()
        val isValidEmail = screenState.isValidEmail
        val hasAt = screenState.hasAt
        val screenSideEffect: HomeScreenSideEffect by screenModel
            .container
            .sideEffectFlow
            .collectAsState(initial = HomeScreenSideEffect.Idle)

        LaunchedEffect(screenSideEffect) {
            when (val sideEffect = screenSideEffect) {
                is HomeScreenSideEffect.NavigateToLogin -> navigator.push(
                    LoginScreen(
                        sideEffect.email
                    )
                )

                is HomeScreenSideEffect.NavigateToSignup -> navigator.push(
                    SignupScreen(
                        sideEffect.email
                    )
                )

                else -> Unit
            }
        }

        var emailTextFieldValue by remember {
            mutableStateOf(TextFieldValue(""))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.waiting))
                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = LottieConstants.IterateForever,
                )
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                )
            }
            Text(
                text = stringResource(R.string.type_your_email_to_start),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .testTag("type_your_email_to_start")
            )
            TextField(
                value = emailTextFieldValue,
                onValueChange = {
                    emailTextFieldValue = it
                    screenModel.onEmailChange(it.text)
                },
                isError = hasAt && !isValidEmail,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("email_text_field")
                    .padding(start = 8.dp, end = 8.dp),
                textStyle = MaterialTheme.typography.labelMedium,
            )
            if (hasAt && !isValidEmail) {
                Text(
                    text = stringResource(R.string.type_a_valid_email),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Button(
                    onClick = {
                        screenModel.onStartClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("start_button"),
                    enabled = hasAt && isValidEmail
                ) {
                    Text(
                        text = stringResource(R.string.start),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    KoinApplication(application = {
        modules(
            CoreModule().module,
            MainModule().module,
        )
    }) {
        Navigator(screen = MainScreen())
    }
}
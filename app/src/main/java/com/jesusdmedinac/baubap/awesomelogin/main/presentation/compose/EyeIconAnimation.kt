package com.jesusdmedinac.baubap.awesomelogin.main.presentation.compose

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jesusdmedinac.baubap.awesomelogin.R

@Composable
fun EyeIconAnimation(
    isPasswordVisible: Boolean = false,
    isPasswordVisibleChange: (Boolean) -> Unit
) {
    IconButton(onClick = {
        isPasswordVisibleChange(!isPasswordVisible)
    }) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.eye
            )
        )
        if (isPasswordVisible) {
            val progress by animateLottieCompositionAsState(
                composition,
                clipSpec = LottieClipSpec.Progress(0.5f, 1f),
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
            )
        } else {
            val progress by animateLottieCompositionAsState(
                composition,
                clipSpec = LottieClipSpec.Progress(0f, 0.5f),
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
            )
        }
    }
}
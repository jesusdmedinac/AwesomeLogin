package com.jesusdmedinac.baubap.awesomelogin.splash.presentation.compose

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay

@Composable
fun AwesomeAnimation(
    modifier: Modifier = Modifier,
    onAnimationEnd: () -> Unit  = {}
) {
    var waveRadiusLimit by remember {
        mutableStateOf(0f)
    }
    var firstWaveRadius by remember {
        mutableStateOf(0f)
    }
    var secondWaveRadius by remember {
        mutableStateOf(0f)
    }
    var tertiaryWaveRadius by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(Unit) {
        while (firstWaveRadius < waveRadiusLimit) {
            delay(10)
            firstWaveRadius += 64f
        }
        while (secondWaveRadius < waveRadiusLimit) {
            delay(10)
            secondWaveRadius += 64f
        }
        while (tertiaryWaveRadius < waveRadiusLimit) {
            delay(10)
            tertiaryWaveRadius += 64f
        }
        onAnimationEnd()
    }
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val backgroundColor = MaterialTheme.colorScheme.background
    Canvas(modifier = modifier) {
        waveRadiusLimit = size.height
        drawCircle(
            primaryColor,
            radius = firstWaveRadius,
            center = Offset(size.width / 2f, size.height / 2f)
        )
        drawCircle(
            secondaryColor,
            radius = secondWaveRadius,
            center = Offset(size.width / 2f, size.height / 2f)
        )
        drawCircle(
            backgroundColor,
            radius = tertiaryWaveRadius,
            center = Offset(size.width / 2f, size.height / 2f)
        )
    }
}
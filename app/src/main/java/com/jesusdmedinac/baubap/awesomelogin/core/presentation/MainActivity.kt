package com.jesusdmedinac.baubap.awesomelogin.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.App
import com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose.ui.theme.AwesomeLoginTheme
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    override fun onRestart() {
        super.onRestart()
        stopKoin()
    }
}

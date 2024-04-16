package com.jesusdmedinac.baubap.awesomelogin.core.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun FunctionNotAvailableAlertDialog(
    visible: Boolean = false,
    onDismissRequest: () -> Unit = {}
) {
    SimpleAlertDialog(
        title = "Esta función no está disponible",
        message = "Nosotros te avisaremos cuando lo esté. Mientras puedes seguir disfrutando de las demás función que tenemos para ti.",
        confirmText = "Gracias",
        visible, onDismissRequest
    )
}

@Composable
fun SimpleAlertDialog(
    title: String,
    message: String,
    confirmText: String,
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    AnimatedVisibility(visible = visible) {
        AlertDialog(
            title = {
                Text(text = title, modifier = Modifier.testTag("simple_alert_dialog_title"))
            },
            text = {
                Text(text = message)
            },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = confirmText, style = MaterialTheme.typography.labelMedium)
                }
            },
        )
    }
}
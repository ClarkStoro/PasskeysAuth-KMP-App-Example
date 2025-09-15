package com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.base.CollectEventsWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    CollectEventsWithLifecycle(viewModel.events, onEvent = { event ->
        onEvent(
            event = event,
            onNavigateToHome = onNavigateToHome
        )
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val username = remember { mutableStateOf("") }
        when {
            uiState.isLoading -> {
                Loading()
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "🔐 Passkey Authentication Demo",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "No passwords needed - your device will handle authentication securely",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(32.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { updatedUsername: String ->
                            username.value = updatedUsername
                        },
                        value = username.value,
                        label = {
                            Text("Insert your username")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        singleLine = true
                    )
                    Spacer(Modifier.height(32.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onUser(AuthActions.LoginPressed(username = username.value))
                        }
                    ) {
                        Text("🔐 LOGIN WITH PASSKEY")
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onUser(AuthActions.RegistrationPressed(username = username.value))
                        }
                    ) {
                        Text("✨ CREATE PASSKEY")
                    }
                }

                when {
                    uiState.isRegistrationSuccess == true -> {
                        ResultDialog("🎉 Passkey Created!", subtitle = "Your device is now your password", isSuccess = true)
                    }
                    uiState.isRegistrationSuccess == false -> {
                        ResultDialog("\uD83D\uDE2D Registration Failed", subtitle = "Please ensure your device supports passkeys", isSuccess = false)
                    }
                    uiState.isLoginSuccess == false -> {
                        ResultDialog("\uD83D\uDE2D Login Failed", subtitle = "Please try authenticating with your device again", isSuccess = false)
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text(
                text = "🔐 Creating your passkey...",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Your device will prompt for biometric authentication",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ResultDialog(title: String, subtitle: String, isSuccess: Boolean) {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface (
            modifier = Modifier
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 32.dp)
            ) {
                Icon(
                    imageVector = if (isSuccess) Icons.Filled.Done else Icons.Filled.Close,
                    contentDescription = "Done",
                    tint = if (isSuccess) Color.Green else Color.Red,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


private fun onEvent(
    event: AuthEvents,
    onNavigateToHome: () -> Unit
) {
    when (event) {
        AuthEvents.NavigateToHome -> {
            onNavigateToHome()
        }
    }
}

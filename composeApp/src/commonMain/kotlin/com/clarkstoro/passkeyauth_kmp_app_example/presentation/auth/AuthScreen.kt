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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { updatedUsername: String ->
                            username.value = updatedUsername
                        },
                        value = username.value,
                        label = {
                            Text("Insert your username")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )
                    Spacer(Modifier.height(32.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onUser(AuthActions.LoginPressed(username = username.value))
                        }
                    ) {
                        Text("LOGIN")
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onUser(AuthActions.RegistrationPressed(username = username.value))
                        }
                    ) {
                        Text("REGISTER")
                    }
                }

                when {
                    uiState.isRegistrationSuccess == true -> {
                        ResultDialog("Registration success!", isSuccess = true)
                    }
                    uiState.isRegistrationSuccess == false -> {
                        ResultDialog("Registration failure!", isSuccess = false)
                    }
                    uiState.isLoginSuccess == false -> {
                        ResultDialog("Login failure!", isSuccess = false)
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ResultDialog(title: String, isSuccess: Boolean) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {}
    ) {
        Surface (
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .padding(32.dp)
            ) {
                Icon(
                    imageVector = if (isSuccess) Icons.Filled.Done else Icons.Filled.Close,
                    contentDescription = "Done",
                    tint = if (isSuccess) Color.Green else Color.Red,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
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

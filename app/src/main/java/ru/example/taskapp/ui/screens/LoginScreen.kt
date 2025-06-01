package ru.example.taskapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.example.taskapp.R
import ru.example.taskapp.ui.components.NextButton
import ru.example.taskapp.ui.components.TextField
import ru.example.taskapp.ui.theme.TaskAppTheme
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors
import ru.example.taskapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        if (viewModel.checkInitialAuthState()) navController.navigate("main") { popUpTo("login") { inclusive = true } }
    }


    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult by viewModel.loginResult.collectAsState()
    val colors = LocalExtendedColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.username_screen),
                style = MaterialTheme.typography.titleSmall,
                color = colors.ming
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = stringResource(R.string.username),
                leadingIcon = painterResource(R.drawable.ic_user)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(R.string.password),
                isPassword = true,
                leadingIcon = painterResource(R.drawable.ic_password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (loginResult) {
                is LoginViewModel.LoginState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoginViewModel.LoginState.Success -> {

                }
                is LoginViewModel.LoginState.Error -> {
                    Text(
                        text = (loginResult as LoginViewModel.LoginState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {

                }
            }

        }
        NextButton(
            text = stringResource(R.string.next),
            onClick = {
                viewModel.login(username, password, navController)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    TaskAppTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}
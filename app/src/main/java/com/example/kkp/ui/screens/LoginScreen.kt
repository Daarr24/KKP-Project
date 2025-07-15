package com.example.kkp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kkp.R
import com.example.kkp.viewmodel.AuthViewModel
import com.example.kkp.viewmodel.LoginState
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.kkp.ui.theme.RedPrimary
import com.example.kkp.ui.theme.GrayDark
import com.example.kkp.ui.theme.WhiteSoft
import com.example.kkp.ui.theme.GrayMedium
import com.example.kkp.ui.theme.White
import androidx.compose.ui.tooling.preview.Preview
import com.example.kkp.ui.theme.KKPTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val loginState by authViewModel.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // SAFE navigation handling - no smart cast issues
    LaunchedEffect(loginState) {
        handleLoginState(loginState, authViewModel, navController)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteSoft)
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )
        // Title
        Text(
            text = "Asset Management",
            style = MaterialTheme.typography.headlineMedium,
            color = RedPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Sign in to your account",
            style = MaterialTheme.typography.bodyLarge,
            color = GrayDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", style = MaterialTheme.typography.labelLarge, color = GrayDark) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = RedPrimary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RedPrimary,
                unfocusedBorderColor = GrayMedium,
                cursorColor = RedPrimary,
                focusedLabelColor = GrayDark,
                unfocusedLabelColor = GrayDark,
                focusedTextColor = GrayDark,
                unfocusedTextColor = GrayDark,
                disabledTextColor = GrayDark,
                disabledLabelColor = GrayDark,
                disabledBorderColor = GrayMedium,
                errorTextColor = RedPrimary
            )
        )
        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", style = MaterialTheme.typography.labelLarge, color = GrayDark) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = RedPrimary
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = RedPrimary
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RedPrimary,
                unfocusedBorderColor = GrayMedium,
                cursorColor = RedPrimary,
                focusedLabelColor = GrayDark,
                unfocusedLabelColor = GrayDark,
                focusedTextColor = GrayDark,
                unfocusedTextColor = GrayDark,
                disabledTextColor = GrayDark,
                disabledLabelColor = GrayDark,
                disabledBorderColor = GrayMedium,
                errorTextColor = RedPrimary
            )
        )
        
        // Login Button
        LoginButton(
            email = email,
            password = password,
            loginState = loginState,
            onLoginClick = { authViewModel.login(email, password) }
        )
        
        // Error Message
        LoginErrorMessage(loginState = loginState)
    }
}

// Separate function to handle login state - avoids smart cast issues
private fun handleLoginState(
    state: LoginState,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    when (state) {
        is LoginState.Success -> {
            Log.d("LoginScreen", "Login successful, navigating to dashboard...")
            authViewModel.resetLoginState()
            navController.navigate("dashboard") {
                popUpTo("login") { inclusive = true }
            }
            Log.d("LoginScreen", "Navigation to dashboard completed")
        }
        is LoginState.Error -> {
            Log.e("LoginScreen", "Login error: ${state.message}")
        }
        is LoginState.Loading -> {
            Log.d("LoginScreen", "Login in progress...")
        }
        else -> {
            Log.d("LoginScreen", "Login state: $state")
        }
    }
}

// Separate composable for login button
@Composable
private fun LoginButton(
    email: String,
    password: String,
    loginState: LoginState,
    onLoginClick: () -> Unit
) {
    val isLoading = loginState is LoginState.Loading
    val isEnabled = email.isNotEmpty() && password.isNotEmpty() && !isLoading
    
    Button(
        onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                onLoginClick()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = White
            )
        } else {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.labelLarge,
                color = White
            )
        }
    }
}

// Separate composable for error message
@Composable
private fun LoginErrorMessage(loginState: LoginState) {
    when (loginState) {
        is LoginState.Error -> {
            Text(
                text = loginState.message,
                color = RedPrimary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        else -> {
            // No error to display
        }
    }
}

@Preview(showBackground = true, name = "LoginScreen Preview")
@Composable
fun PreviewLoginScreen() {
    KKPTheme {
        Box(Modifier.height(600.dp).fillMaxWidth()) {
            LoginScreen(
                authViewModel = viewModel(),
                navController = rememberNavController()
            )
        }
    }
} 
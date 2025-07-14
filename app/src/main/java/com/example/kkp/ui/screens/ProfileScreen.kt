package com.example.kkp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kkp.ui.theme.*
import com.example.kkp.viewmodel.AuthViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.kkp.ui.theme.KKPTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    val userInfo by authViewModel.userInfo.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile", style = MaterialTheme.typography.headlineSmall, color = RedPrimary) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = RedPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = RedPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                    titleContentColor = RedPrimary
                )
            )
        },
        containerColor = WhiteSoft
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Card(
                modifier = Modifier
                    .size(140.dp)
                    .padding(top = 32.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = RedPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = White,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
            // User Name
            Text(
                text = userInfo?.second ?: "Danu Febri Andi Prasetyo",
                style = MaterialTheme.typography.headlineSmall,
                color = RedPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            // User Email
            Text(
                text = userInfo?.third ?: "danuprasetya573@gmail.com",
                style = MaterialTheme.typography.bodyLarge,
                color = GrayDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            // User Level/Role
            Card(
                modifier = Modifier.padding(top = 12.dp),
                colors = CardDefaults.cardColors(containerColor = RedPrimary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Asset Manager",
                    style = MaterialTheme.typography.labelLarge,
                    color = RedPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            // Additional Profile Information
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Profile Information",
                        style = MaterialTheme.typography.titleLarge,
                        color = RedPrimary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    ProfileInfoRow("User ID", userInfo?.first?.toString() ?: "1")
                    ProfileInfoRow("Email Verified", if (userInfo?.first != null) "Yes" else "No")
                    ProfileInfoRow("Member Since", "2025-06-28")
                    ProfileInfoRow("Last Updated", "2025-06-28")
                }
            }
            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { /* TODO: Edit Profile */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = White,
                        contentColor = RedPrimary
                    ),
                    border = androidx.compose.foundation.BorderStroke(2.dp, RedPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Edit Profile", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = onLogout,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Logout", style = MaterialTheme.typography.labelLarge, color = White)
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = GrayDark,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = RedPrimary,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
} 

@Preview(showBackground = true, name = "ProfileScreen Preview")
@Composable
fun PreviewProfileScreen() {
    KKPTheme {
        Box(Modifier.height(600.dp).fillMaxWidth()) {
            ProfileScreen(
                onNavigateBack = {},
                onLogout = {},
                authViewModel = viewModel() // Jika error, bisa gunakan mock AuthViewModel()
            )
        }
    }
} 
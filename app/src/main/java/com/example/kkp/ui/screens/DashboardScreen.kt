package com.example.kkp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kkp.ui.theme.*
import com.example.kkp.viewmodel.AuthViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.kkp.ui.theme.KKPTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    onNavigateToAssets: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToRental: () -> Unit,
    onNavigateToPengiriman: () -> Unit,
    onNavigateToTagihan: () -> Unit,
    onNavigateToProfile: () -> Unit,
    authViewModel: AuthViewModel
) {
    val userInfo by authViewModel.userInfo.collectAsStateWithLifecycle()
    
    val menuItems = rememberMenuItems(
        onNavigateToAssets = onNavigateToAssets,
        onNavigateToProjects = onNavigateToProjects,
        onNavigateToRental = onNavigateToRental,
        onNavigateToPengiriman = onNavigateToPengiriman,
        onNavigateToTagihan = onNavigateToTagihan,
        onNavigateToProfile = onNavigateToProfile
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asset Management", style = MaterialTheme.typography.headlineSmall, color = RedPrimary) },
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
                .background(WhiteSoft)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Welcome Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = RedPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = userInfo?.second ?: "Danu Febri Andi Prasetyo",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Black
                    )
                    Text(
                        text = userInfo?.third ?: "danuprasetya573@gmail.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GrayDark
                    )
                }
            }
            // Visualizations
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Assets by Category",
                            style = MaterialTheme.typography.titleLarge,
                            color = RedPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Placeholder for chart
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(RedPrimary.copy(alpha = 0.08f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Chart Placeholder",
                                color = GrayDark,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Rental Status",
                            style = MaterialTheme.typography.titleLarge,
                            color = BlueInfo
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Placeholder for chart
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(BlueInfo.copy(alpha = 0.08f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Chart Placeholder",
                                color = GrayDark,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            // Menu Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(menuItems) { menuItem ->
                    MenuCard(
                        menuItem = menuItem
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(
    menuItem: MenuItem
) {
    Card(
        onClick = menuItem.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    listOf(NeonBlue.copy(alpha = 0.2f), NeonPurple.copy(alpha = 0.2f), NeonGreen.copy(alpha = 0.2f))
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = CardBg.copy(alpha = 0.85f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.title,
                modifier = Modifier.size(48.dp),
                tint = NeonBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = menuItem.description,
                style = MaterialTheme.typography.bodySmall,
                color = NeonGreen,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val description: String,
    val onClick: () -> Unit
)

@Composable
fun rememberMenuItems(
    onNavigateToAssets: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToRental: () -> Unit,
    onNavigateToPengiriman: () -> Unit,
    onNavigateToTagihan: () -> Unit,
    onNavigateToProfile: () -> Unit
): List<MenuItem> {
    return listOf(
        MenuItem(
            title = "Assets",
            icon = Icons.Default.Computer,
            description = "Manage assets and inventory",
            onClick = onNavigateToAssets
        ),
        MenuItem(
            title = "Projects",
            icon = Icons.Default.Business,
            description = "Manage projects and contracts",
            onClick = onNavigateToProjects
        ),
        MenuItem(
            title = "Rental",
            icon = Icons.Default.Assignment,
            description = "Manage rental agreements",
            onClick = onNavigateToRental
        ),
        MenuItem(
            title = "Pengiriman",
            icon = Icons.Default.LocalShipping,
            description = "Track deliveries and shipments",
            onClick = onNavigateToPengiriman
        ),
        MenuItem(
            title = "Tagihan",
            icon = Icons.Default.Receipt,
            description = "Manage invoices and billing",
            onClick = onNavigateToTagihan
        ),
        MenuItem(
            title = "Profile",
            icon = Icons.Default.Person,
            description = "View and edit profile",
            onClick = onNavigateToProfile
        )
    )
} 

@Preview(showBackground = true, name = "DashboardScreen Preview")
@Composable
fun PreviewDashboardScreen() {
    KKPTheme {
        Box(Modifier.height(600.dp).fillMaxWidth()) {
            DashboardScreen(
                onLogout = {},
                onNavigateToAssets = {},
                onNavigateToProjects = {},
                onNavigateToRental = {},
                onNavigateToPengiriman = {},
                onNavigateToTagihan = {},
                onNavigateToProfile = {},
                authViewModel = viewModel() // Jika error, bisa gunakan mock AuthViewModel()
            )
        }
    }
} 
package com.example.kkp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kkp.viewmodel.AuthViewModel

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
                title = { Text("Asset Management") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Welcome Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = userInfo?.second ?: "User",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = userInfo?.third ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
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
                        title = menuItem.title,
                        icon = menuItem.icon,
                        description = menuItem.description,
                        onClick = menuItem.onClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(
    title: String,
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
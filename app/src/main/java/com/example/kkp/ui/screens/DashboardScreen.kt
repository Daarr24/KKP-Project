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
import com.example.kkp.model.DashboardData
import androidx.compose.runtime.LaunchedEffect
import android.util.Log

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
    val dashboardData by authViewModel.dashboardData.collectAsStateWithLifecycle()
    val dashboardLoading by authViewModel.dashboardLoading.collectAsStateWithLifecycle()
    val dashboardError by authViewModel.dashboardError.collectAsStateWithLifecycle()

    // Fetch dashboard data in background, don't block UI
    LaunchedEffect(Unit) {
        Log.d("DashboardScreen", "DashboardScreen loaded successfully!")
        try {
            Log.d("DashboardScreen", "Fetching dashboard data...")
            authViewModel.fetchDashboard()
        } catch (e: Exception) {
            Log.e("DashboardScreen", "Error fetching dashboard: ${e.message}")
            // Ignore error, allow UI to show without dashboard data
        }
    }
    
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
                        text = "✅ Dashboard Loaded Successfully!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = RedPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.headlineMedium,
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
            // Statistik utama - selalu tampilkan, dengan data atau placeholder
            when {
                dashboardData != null -> {
                    // Tampilkan data real
                    Row(
                        Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Asset", style = MaterialTheme.typography.titleMedium, color = RedPrimary)
                            Text(dashboardData!!.totalAsset.toString(), style = MaterialTheme.typography.headlineMedium, color = Black)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Rental", style = MaterialTheme.typography.titleMedium, color = BlueInfo)
                            Text(dashboardData!!.totalRental.toString(), style = MaterialTheme.typography.headlineMedium, color = Black)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Bulan", style = MaterialTheme.typography.titleMedium, color = NeonGreen)
                            Text("${dashboardData!!.monthName} ${dashboardData!!.year}", style = MaterialTheme.typography.bodyLarge, color = Black)
                        }
                    }
                    // Bar Chart
                    BarChartSection(dashboardData!!)
                    Spacer(Modifier.height(16.dp))
                    // Pie Chart
                    PieChartSection(dashboardData!!)
                    Spacer(Modifier.height(16.dp))
                }
                dashboardLoading -> {
                    // Tampilkan skeleton loading
                    Row(
                        Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Asset", style = MaterialTheme.typography.titleMedium, color = RedPrimary)
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = RedPrimary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Rental", style = MaterialTheme.typography.titleMedium, color = BlueInfo)
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = BlueInfo)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Loading...", style = MaterialTheme.typography.titleMedium, color = NeonGreen)
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = NeonGreen)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
                else -> {
                    // Tampilkan placeholder atau error message tanpa memblokir UI
                    Row(
                        Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Asset", style = MaterialTheme.typography.titleMedium, color = RedPrimary)
                            Text("--", style = MaterialTheme.typography.headlineMedium, color = GrayDark)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Rental", style = MaterialTheme.typography.titleMedium, color = BlueInfo)
                            Text("--", style = MaterialTheme.typography.headlineMedium, color = GrayDark)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Dashboard", style = MaterialTheme.typography.titleMedium, color = NeonGreen)
                            Text("Unavailable", style = MaterialTheme.typography.bodyLarge, color = GrayDark)
                        }
                    }
                    if (dashboardError != null) {
                        Text(
                            text = "Dashboard data unavailable: ${dashboardError}",
                            style = MaterialTheme.typography.bodySmall,
                            color = RedPrimary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
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

@Composable
fun BarChartSection(data: DashboardData) {
    // Implementasi bar chart sederhana (custom drawing atau gunakan library jika ada)
    // Untuk demo, tampilkan data sebagai teks
    Column(Modifier.fillMaxWidth().background(White, RoundedCornerShape(12.dp)).padding(16.dp)) {
        Text("Asset & Rental per Bulan", style = MaterialTheme.typography.titleMedium, color = RedPrimary)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            data.months.forEachIndexed { idx, month ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(month, style = MaterialTheme.typography.bodySmall)
                    // Bar asset
                    Box(Modifier.width(12.dp).height((data.assetPerMonth[idx] * 10).dp).background(RedPrimary, RoundedCornerShape(4.dp)))
                    // Bar rental
                    Box(Modifier.width(12.dp).height((data.rentPerMonth[idx] * 10).dp).background(BlueInfo, RoundedCornerShape(4.dp)).padding(top = 2.dp))
                }
            }
        }
    }
}

@Composable
fun PieChartSection(data: DashboardData) {
    // Implementasi pie chart sederhana (custom drawing atau gunakan library jika ada)
    // Untuk demo, tampilkan data sebagai list warna dan label
    Column(Modifier.fillMaxWidth().background(White, RoundedCornerShape(12.dp)).padding(16.dp)) {
        Text("Distribusi Asset", style = MaterialTheme.typography.titleMedium, color = RedPrimary)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            data.pieLabels.forEachIndexed { idx, label ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.size(24.dp).background(Color(android.graphics.Color.parseColor(data.pieColors[idx])), RoundedCornerShape(12.dp)))
                    Text(label, style = MaterialTheme.typography.bodySmall)
                    Text(data.pieData[idx].toString(), style = MaterialTheme.typography.bodySmall, color = Black)
                }
            }
        }
    }
} 
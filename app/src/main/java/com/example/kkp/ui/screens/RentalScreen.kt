package com.example.kkp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kkp.model.Rental
import com.example.kkp.ui.theme.*
import com.example.kkp.viewmodel.RentalViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.kkp.ui.theme.KKPTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalScreen(
    onNavigateToProfile: () -> Unit = {},
    onLogout: () -> Unit = {},
    rentalViewModel: RentalViewModel = viewModel()
) {
    val rentals by rentalViewModel.rentals.collectAsStateWithLifecycle()
    val activeRentals by rentalViewModel.activeRentals.collectAsStateWithLifecycle()
    val upcomingRentals by rentalViewModel.upcomingRentals.collectAsStateWithLifecycle()
    val returnRentals by rentalViewModel.returnRentals.collectAsStateWithLifecycle()
    val rentalState by rentalViewModel.rentalState.collectAsStateWithLifecycle()
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var expanded by remember { mutableStateOf(false) }
    
    val filterOptions = listOf("All", "Ready", "Rent", "In Use", "Maintenance")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rentals Inventory", style = MaterialTheme.typography.headlineSmall, color = RedPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                    titleContentColor = RedPrimary
                )
            )
        },
        containerColor = WhiteSoft
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Statistics Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Active Rental",
                        value = activeRentals.toString(),
                        backgroundColor = RedPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Upcoming Rental",
                        value = upcomingRentals.toString(),
                        backgroundColor = BlueInfo,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Return Rental",
                        value = returnRentals.toString(),
                        backgroundColor = YellowWarning,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            // Search & Filter
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Cari rental", style = MaterialTheme.typography.bodyMedium) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = RedPrimary) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RedPrimary,
                            unfocusedBorderColor = GrayMedium,
                            cursorColor = RedPrimary
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    // Filter Dropdown
                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = White,
                                contentColor = RedPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp, RedPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(selectedFilter, style = MaterialTheme.typography.labelLarge)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Filter", tint = RedPrimary)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(White)
                        ) {
                            filterOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                                    onClick = {
                                        selectedFilter = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    OutlinedButton(
                        onClick = { /* TODO: Implement more filter */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = White,
                            contentColor = RedPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, RedPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Filter Lain", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
            // Rental Table
            item {
                RentalTable(rentals = rentals)
            }
        }
        // Bottom Toolbar
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        color = RedPrimary,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onNavigateToProfile() }
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = White,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Profil", color = White, style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(containerColor = White),
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout", tint = RedPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Logout", color = RedPrimary, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RentalTable(rentals: List<Rental>) {
    val tableHeaders = listOf(
        "Id", "Pengiriman ID", "Project ID", "Tanggal Mulai", 
        "Tanggal Selesai", "Status", "Total Tagihan", "Created At"
    )
    
    // Sample data for demonstration (replace with actual rental data)
    val sampleRentals = if (rentals.isEmpty()) {
        listOf(
            Rental(
                id = 1,
                pengirimanId = 1,
                projectId = 1,
                status = "aktif",
                periodeMulai = "2025-06-29",
                periodeAkhir = "2025-08-29",
                totalTagihan = 550000,
                createdAt = "2025-06-28 13:54:44",
                updatedAt = "2025-06-28 13:54:44"
            ),
            Rental(
                id = 2,
                pengirimanId = 2,
                projectId = 1,
                status = "aktif",
                periodeMulai = "2025-06-29",
                periodeAkhir = "2025-08-29",
                totalTagihan = 550000,
                createdAt = "2025-06-28 13:55:05",
                updatedAt = "2025-06-28 13:55:05"
            )
        )
    } else {
        rentals
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(1.dp)
        ) {
            // Table Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2C3E50)) // Dark grey background
                        .padding(12.dp)
                ) {
                    tableHeaders.forEach { header ->
                        Text(
                            text = header,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // Table Rows
            items(sampleRentals) { rental ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.05f))
                        .padding(12.dp)
                ) {
                    Text(
                        text = rental.id.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.pengirimanId.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.projectId.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.periodeMulai,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.periodeAkhir,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.status,
                        color = when (rental.status) {
                            "aktif" -> NeonGreen
                            "selesai" -> NeonPink
                            else -> Color.White
                        },
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Rp ${rental.totalTagihan}",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.createdAt ?: "",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
} 

@Preview(showBackground = true, name = "RentalScreen Preview")
@Composable
fun PreviewRentalScreen() {
    KKPTheme {
        Box(Modifier.height(600.dp).fillMaxWidth()) {
            RentalScreen()
        }
    }
} 
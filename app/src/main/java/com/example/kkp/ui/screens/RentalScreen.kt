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
                title = { Text("Rentals Inventory") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GlassBg.copy(alpha = 0.85f)
                )
            )
        },
        containerColor = GlassBg
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Statistics Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        title = "Active Rental",
                        value = activeRentals.toString(),
                        backgroundColor = NeonBlue,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Upcoming Rental",
                        value = upcomingRentals.toString(),
                        backgroundColor = NeonGreen,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Return Rental",
                        value = returnRentals.toString(),
                        backgroundColor = Color(0xFFFF8C00), // Orange
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Search & Filter
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search rentals") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonBlue,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedLabelColor = NeonBlue,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            cursorColor = NeonBlue,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    // Filter Dropdown
                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White.copy(alpha = 0.1f),
                                contentColor = Color.White
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp, Color.White.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(selectedFilter)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Filter")
                        }
                        
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(CardBg)
                        ) {
                            filterOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, color = Color.White) },
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
                            containerColor = Color.White.copy(alpha = 0.1f),
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, Color.White.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("More Filter")
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
                        color = Color(0xFFE74C3C), // Red background
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(12.dp),
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
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Profile",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                // Logout Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onLogout() }
                ) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
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
        "Id", "Asset ID", "Project ID", "Tanggal Mulai", 
        "Tanggal Selesai", "Status", "Created At", "Updated At"
    )
    
    // Sample data for demonstration (replace with actual rental data)
    val sampleRentals = if (rentals.isEmpty()) {
        listOf(
            Rental(
                id = 1,
                pengirimanId = 1,
                projectId = 1001,
                tagihanId = 1,
                status = "aktif",
                periodeMulai = "2024-01-15",
                periodeAkhir = "2024-01-20",
                jumlahUnit = 5,
                totalTagihan = 1000000,
                createdAt = "2024-01-01 10:00",
                updatedAt = "2024-01-15 08:30"
            ),
            Rental(
                id = 2,
                pengirimanId = 2,
                projectId = 1002,
                tagihanId = 2,
                status = "aktif",
                periodeMulai = "2024-02-01",
                periodeAkhir = "2024-02-10",
                jumlahUnit = 3,
                totalTagihan = 750000,
                createdAt = "2024-01-25 11:00",
                updatedAt = "2024-02-01 09:15"
            ),
            Rental(
                id = 3,
                pengirimanId = 3,
                projectId = 1003,
                tagihanId = 3,
                status = "selesai",
                periodeMulai = "2024-03-05",
                periodeAkhir = "2024-03-12",
                jumlahUnit = 2,
                totalTagihan = 500000,
                createdAt = "2024-02-28 14:30",
                updatedAt = "2024-03-05 13:00"
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
                        text = rental.createdAt ?: "",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = rental.updatedAt ?: "",
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
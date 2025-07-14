package com.example.kkp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kkp.model.Asset
import com.example.kkp.viewmodel.AssetViewModel
import com.example.kkp.viewmodel.AssetState
import com.example.kkp.ui.theme.GrayDark
import com.example.kkp.ui.theme.RedPrimary
import com.example.kkp.ui.theme.White
import com.example.kkp.ui.theme.WhiteSoft
import com.example.kkp.ui.theme.YellowWarning
import androidx.compose.ui.tooling.preview.Preview
import com.example.kkp.ui.theme.KKPTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetScreen() {
    val assetViewModel: AssetViewModel = viewModel()
    val assets by assetViewModel.assets.collectAsStateWithLifecycle()
    val assetState by assetViewModel.assetState.collectAsStateWithLifecycle()
    
    var showAddDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asset Management", style = MaterialTheme.typography.headlineSmall, color = RedPrimary) },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Asset", tint = RedPrimary)
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
                .padding(20.dp)
        ) {
            when (assetState) {
                is AssetState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = RedPrimary)
                    }
                }
                is AssetState.Error -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = YellowWarning
                        )
                    ) {
                        Text(
                            text = (assetState as AssetState.Error).message,
                            modifier = Modifier.padding(16.dp),
                            color = RedPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                else -> {
                    if (assets.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Computer,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = GrayDark
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No assets found",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = GrayDark
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tap the + button to add your first asset",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = GrayDark
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(0.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(assets) { asset ->
                                AssetCard(asset = asset)
                            }
                        }
                    }
                }
            }
        }
        if (showAddDialog) {
            AddAssetDialog(
                onDismiss = { showAddDialog = false },
                onAssetAdded = { merk, type, spesifikasi ->
                    assetViewModel.createAsset(merk, type, spesifikasi)
                    showAddDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetCard(asset: Asset) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${asset.merk} - ${asset.type}",
                        style = MaterialTheme.typography.titleLarge,
                        color = RedPrimary
                    )
                    Text(
                        text = "ID: ${asset.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GrayDark
                    )
                }
                Icon(
                    imageVector = Icons.Default.Computer,
                    contentDescription = null,
                    tint = RedPrimary
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Specifications:",
                style = MaterialTheme.typography.labelLarge,
                color = RedPrimary
            )
            Text(
                text = asset.spesifikasi,
                style = MaterialTheme.typography.bodySmall,
                color = GrayDark
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Created: ${asset.createdAt ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall,
                color = GrayDark
            )
            if (asset.detailAssets?.isNotEmpty() == true) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Detail Assets: ${asset.detailAssets.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = RedPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetDialog(
    onDismiss: () -> Unit,
    onAssetAdded: (String, String, String) -> Unit
) {
    var merk by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var spesifikasi by remember { mutableStateOf("") }
    var selectedMerk by remember { mutableStateOf("") }
    
    val merkOptions = listOf("Asus", "Acer", "Dell", "Hp", "Lenovo")
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Asset") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Brand Dropdown
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { },
                ) {
                    OutlinedTextField(
                        value = selectedMerk,
                        onValueChange = { selectedMerk = it },
                        label = { Text("Brand") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        modifier = Modifier.menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = { },
                    ) {
                        merkOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { selectedMerk = option }
                            )
                        }
                    }
                }
                
                // Type Field
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Specifications Field
                OutlinedTextField(
                    value = spesifikasi,
                    onValueChange = { spesifikasi = it },
                    label = { Text("Specifications") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (selectedMerk.isNotEmpty() && type.isNotEmpty() && spesifikasi.isNotEmpty()) {
                        onAssetAdded(selectedMerk, type, spesifikasi)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 

@Preview(showBackground = true, name = "AssetScreen Preview")
@Composable
fun PreviewAssetScreen() {
    KKPTheme {
        Box(Modifier.height(600.dp).fillMaxWidth()) {
            AssetScreen()
        }
    }
} 
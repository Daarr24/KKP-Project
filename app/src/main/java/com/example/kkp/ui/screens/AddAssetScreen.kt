package com.example.kkp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kkp.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetScreen(
    onCancel: () -> Unit = {},
    onAddAsset: (String, String, String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _, _, _ -> }
) {
    var merk by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var serialNumber by remember { mutableStateOf("") }
    var spesifikasi by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("Normal") }
    var note by remember { mutableStateOf("") }
    val conditionOptions = listOf("Normal", "Rusak")
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(GlassBg, NeonBlue.copy(alpha = 0.1f), NeonPurple.copy(alpha = 0.1f))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(CardBg.copy(alpha = 0.95f))
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add New Asset",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = NeonBlue
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Form Grid (2 columns)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = merk,
                        onValueChange = { merk = it },
                        label = { Text("Merk*") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = serialNumber,
                        onValueChange = { serialNumber = it },
                        label = { Text("Serial Number*") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = purchaseDate,
                        onValueChange = {},
                        label = { Text("Purchase Date*") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.CalendarToday, contentDescription = "Pick Date")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Condition Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = condition,
                            onValueChange = {},
                            label = { Text("Condition*") },
                            readOnly = true,
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            conditionOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        condition = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = type,
                        onValueChange = { type = it },
                        label = { Text("Type*") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = spesifikasi,
                        onValueChange = { spesifikasi = it },
                        label = { Text("Spesifikasi*") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = purchasePrice,
                        onValueChange = { purchasePrice = it },
                        label = { Text("Purchase Price*") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                visualTransformation = VisualTransformation.None,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("Cancel", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        onAddAsset(
                            merk, type, serialNumber, spesifikasi, purchaseDate, purchasePrice, condition, note
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonBlue)
                ) {
                    Text("Add Asset", color = Color.White)
                }
            }
        }
    }
    // Date Picker (simple)
    if (showDatePicker) {
        val today = remember { Calendar.getInstance() }
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            onDateSelected = {
                purchaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.time)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false },
            initialYear = year,
            initialMonth = month,
            initialDay = day
        )
    }
}

@Composable
fun DatePickerDialog(
    onDateSelected: (Calendar) -> Unit,
    onDismiss: () -> Unit,
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int
) {
    // You can use a library or custom implementation for a better date picker
    // For now, just call onDateSelected with today
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pick a Date") },
        text = { Text("(Date picker implementation needed)") },
        confirmButton = {
            TextButton(onClick = {
                val cal = Calendar.getInstance()
                cal.set(initialYear, initialMonth, initialDay)
                onDateSelected(cal)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
} 
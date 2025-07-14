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
import androidx.compose.ui.tooling.preview.Preview
import com.example.kkp.ui.theme.KKPTheme

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
            .background(WhiteSoft)
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                    text = "Tambah Asset Baru",
                    style = MaterialTheme.typography.headlineMedium,
                    color = RedPrimary
            )
                Spacer(modifier = Modifier.height(32.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = merk,
                        onValueChange = { merk = it },
                            label = { Text("Merk*", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RedPrimary,
                                unfocusedBorderColor = GrayMedium,
                                cursorColor = RedPrimary
                            )
                    )
                        Spacer(modifier = Modifier.height(18.dp))
                    OutlinedTextField(
                        value = serialNumber,
                        onValueChange = { serialNumber = it },
                            label = { Text("Serial Number*", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RedPrimary,
                                unfocusedBorderColor = GrayMedium,
                                cursorColor = RedPrimary
                            )
                    )
                        Spacer(modifier = Modifier.height(18.dp))
                    OutlinedTextField(
                        value = purchaseDate,
                        onValueChange = {},
                            label = { Text("Tanggal Beli*", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                    Icon(Icons.Default.CalendarToday, contentDescription = "Pick Date", tint = RedPrimary)
                            }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RedPrimary,
                                unfocusedBorderColor = GrayMedium,
                                cursorColor = RedPrimary
                            )
                    )
                        Spacer(modifier = Modifier.height(18.dp))
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = condition,
                            onValueChange = {},
                                label = { Text("Kondisi*", style = MaterialTheme.typography.labelLarge) },
                            readOnly = true,
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = RedPrimary,
                                    unfocusedBorderColor = GrayMedium,
                                    cursorColor = RedPrimary
                                )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(White)
                        ) {
                            conditionOptions.forEach { option ->
                                DropdownMenuItem(
                                        text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
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
                            label = { Text("Tipe*", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RedPrimary,
                                unfocusedBorderColor = GrayMedium,
                                cursorColor = RedPrimary
                            )
                    )
                        Spacer(modifier = Modifier.height(18.dp))
                    OutlinedTextField(
                        value = spesifikasi,
                        onValueChange = { spesifikasi = it },
                            label = { Text("Spesifikasi*", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RedPrimary,
                                unfocusedBorderColor = GrayMedium,
                                cursorColor = RedPrimary
                            )
                    )
                        Spacer(modifier = Modifier.height(18.dp))
                    OutlinedTextField(
                        value = purchasePrice,
                        onValueChange = { purchasePrice = it },
                            label = { Text("Harga Beli*", style = MaterialTheme.typography.labelLarge) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RedPrimary,
                                unfocusedBorderColor = GrayMedium,
                                cursorColor = RedPrimary
                            )
                    )
                }
            }
                Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                    label = { Text("Catatan*", style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                visualTransformation = VisualTransformation.None,
                    maxLines = 5,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RedPrimary,
                        unfocusedBorderColor = GrayMedium,
                        cursorColor = RedPrimary
                    )
            )
                Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                    OutlinedButton(
                    onClick = onCancel,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = White,
                            contentColor = RedPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(2.dp, RedPrimary),
                        shape = RoundedCornerShape(16.dp)
                ) {
                        Text("Batal", style = MaterialTheme.typography.labelLarge)
                }
                    Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        onAddAsset(
                            merk, type, serialNumber, spesifikasi, purchaseDate, purchasePrice, condition, note
                        )
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                        Text("Tambah Asset", style = MaterialTheme.typography.labelLarge, color = White)
                    }
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

@Preview(showBackground = true, name = "AddAssetScreen Preview")
@Composable
fun PreviewAddAssetScreen() {
    KKPTheme {
        Box(Modifier.height(600.dp).fillMaxWidth()) {
            AddAssetScreen()
        }
    }
} 
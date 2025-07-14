package com.example.kkp.model

import com.google.gson.annotations.SerializedName

// Response utama dari /api/vcom/dashboard
// Contoh JSON:
// {"status":"success","message":"Dashboard data fetched successfully","data":{...}}
data class DashboardResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DashboardData
)

data class DashboardData(
    @SerializedName("totalasset")
    val totalAsset: Int,
    @SerializedName("totalrental")
    val totalRental: Int,
    @SerializedName("months")
    val months: List<String>,
    @SerializedName("assetPerMonth")
    val assetPerMonth: List<Int>,
    @SerializedName("rentPerMonth")
    val rentPerMonth: List<Int>,
    @SerializedName("pieLabels")
    val pieLabels: List<String>,
    @SerializedName("pieData")
    val pieData: List<Int>,
    @SerializedName("pieColors")
    val pieColors: List<String>,
    @SerializedName("monthName")
    val monthName: String,
    @SerializedName("year")
    val year: Int
) 
package com.example.kkp.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Project(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("nama")
    val nama: String,
    
    @SerializedName("durasi_kontrak")
    val durasiKontrak: Int,
    
    @SerializedName("harga_sewa")
    val hargaSewa: BigDecimal,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?
)

data class ProjectRequest(
    @SerializedName("nama")
    val nama: String,
    
    @SerializedName("durasi_kontrak")
    val durasiKontrak: Int,
    
    @SerializedName("harga_sewa")
    val hargaSewa: BigDecimal
) 
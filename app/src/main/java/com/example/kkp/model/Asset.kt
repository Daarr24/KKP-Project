package com.example.kkp.model

import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("merk")
    val merk: String, // Asus, Acer, Dell, Hp, Lenovo
    
    @SerializedName("type")
    val type: String,
    
    @SerializedName("spesifikasi")
    val spesifikasi: String,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?,
    
    @SerializedName("detailassets")
    val detailassets: List<DetailAsset>? = null
)

data class DetailAsset(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("asset_id")
    val assetId: Long,
    
    @SerializedName("serialnumber")
    val serialNumber: String,
    
    @SerializedName("kondisi")
    val kondisi: String, // normal, rusak
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?,
    
    @SerializedName("asset")
    val asset: Asset? = null
)

data class AssetRequest(
    @SerializedName("merk")
    val merk: String,
    
    @SerializedName("type")
    val type: String,
    
    @SerializedName("spesifikasi")
    val spesifikasi: String
)

data class DetailAssetRequest(
    @SerializedName("asset_id")
    val assetId: Long,
    
    @SerializedName("serialnumber")
    val serialNumber: String,
    
    @SerializedName("kondisi")
    val kondisi: String
) 
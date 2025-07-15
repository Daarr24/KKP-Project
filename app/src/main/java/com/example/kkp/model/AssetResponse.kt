package com.example.kkp.model

import com.google.gson.annotations.SerializedName

// Response wrapper untuk list asset
 
data class AssetListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<Asset>
) 
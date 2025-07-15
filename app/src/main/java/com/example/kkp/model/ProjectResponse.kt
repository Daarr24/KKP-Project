package com.example.kkp.model

import com.google.gson.annotations.SerializedName

// Response wrapper untuk list project
data class ProjectListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<Project>
)

// Response wrapper untuk detail project
data class ProjectDetailResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: Project
) 
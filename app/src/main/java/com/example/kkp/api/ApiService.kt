package com.example.kkp.api

import com.example.kkp.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication
    @POST("vcom/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    // Users
    @GET("vcom/users")
    suspend fun getUsers(): Response<List<User>>
    
    @GET("vcom/users/{id}")
    suspend fun getUser(@Path("id") id: Long): Response<User>
    
    // Assets
    @GET("vcom/assets")
    suspend fun getAssets(): Response<List<Asset>>
    
    @GET("vcom/assets/{id}")
    suspend fun getAsset(@Path("id") id: Long): Response<Asset>
    
    @POST("vcom/assets")
    suspend fun createAsset(@Body assetRequest: AssetRequest): Response<Asset>
    
    @PUT("vcom/assets/{id}")
    suspend fun updateAsset(@Path("id") id: Long, @Body assetRequest: AssetRequest): Response<Asset>
    
    @DELETE("vcom/assets/{id}")
    suspend fun deleteAsset(@Path("id") id: Long): Response<Unit>
    
    // Detail Assets
    @GET("vcom/detail-assets")
    suspend fun getDetailAssets(): Response<List<DetailAsset>>
    
    @GET("vcom/detail-assets/{id}")
    suspend fun getDetailAsset(@Path("id") id: Long): Response<DetailAsset>
    
    @POST("vcom/detail-assets")
    suspend fun createDetailAsset(@Body detailAssetRequest: DetailAssetRequest): Response<DetailAsset>
    
    @PUT("vcom/detail-assets/{id}")
    suspend fun updateDetailAsset(@Path("id") id: Long, @Body detailAssetRequest: DetailAssetRequest): Response<DetailAsset>
    
    @DELETE("vcom/detail-assets/{id}")
    suspend fun deleteDetailAsset(@Path("id") id: Long): Response<Unit>
    
    // Projects
    @GET("vcom/projects")
    suspend fun getProjects(): Response<List<Project>>
    
    @GET("vcom/projects/{id}")
    suspend fun getProject(@Path("id") id: Long): Response<Project>
    
    @POST("vcom/projects")
    suspend fun createProject(@Body projectRequest: ProjectRequest): Response<Project>
    
    @PUT("vcom/projects/{id}")
    suspend fun updateProject(@Path("id") id: Long, @Body projectRequest: ProjectRequest): Response<Project>
    
    @DELETE("vcom/projects/{id}")
    suspend fun deleteProject(@Path("id") id: Long): Response<Unit>
    
    // Pengiriman
    @GET("vcom/pengiriman")
    suspend fun getPengiriman(): Response<List<Pengiriman>>
    
    @GET("vcom/pengiriman/{id}")
    suspend fun getPengirimanById(@Path("id") id: Long): Response<Pengiriman>
    
    @POST("vcom/pengiriman")
    suspend fun createPengiriman(@Body pengirimanRequest: PengirimanRequest): Response<Pengiriman>
    
    @PUT("vcom/pengiriman/{id}")
    suspend fun updatePengiriman(@Path("id") id: Long, @Body pengirimanRequest: PengirimanRequest): Response<Pengiriman>
    
    @DELETE("vcom/pengiriman/{id}")
    suspend fun deletePengiriman(@Path("id") id: Long): Response<Unit>
    
    // Tagihan
    @GET("vcom/tagihan")
    suspend fun getTagihan(): Response<List<Tagihan>>
    
    @GET("vcom/tagihan/{id}")
    suspend fun getTagihanById(@Path("id") id: Long): Response<Tagihan>
    
    @POST("vcom/tagihan")
    suspend fun createTagihan(@Body tagihanRequest: TagihanRequest): Response<Tagihan>
    
    @PUT("vcom/tagihan/{id}")
    suspend fun updateTagihan(@Path("id") id: Long, @Body tagihanRequest: TagihanRequest): Response<Tagihan>
    
    @DELETE("vcom/tagihan/{id}")
    suspend fun deleteTagihan(@Path("id") id: Long): Response<Unit>
    
    // Rental
    @GET("vcom/rental")
    suspend fun getRental(): Response<List<Rental>>
    
    @GET("vcom/rental/{id}")
    suspend fun getRentalById(@Path("id") id: Long): Response<Rental>
    
    @POST("vcom/rental")
    suspend fun createRental(@Body rentalRequest: RentalRequest): Response<Rental>
    
    @PUT("vcom/rental/{id}")
    suspend fun updateRental(@Path("id") id: Long, @Body rentalRequest: RentalRequest): Response<Rental>
    
    @DELETE("vcom/rental/{id}")
    suspend fun deleteRental(@Path("id") id: Long): Response<Unit>
} 
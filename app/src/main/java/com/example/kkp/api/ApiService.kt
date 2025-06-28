package com.example.kkp.api

import com.example.kkp.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    
    // Users
    @GET("api/users")
    suspend fun getUsers(): Response<List<User>>
    
    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): Response<User>
    
    // Assets
    @GET("api/assets")
    suspend fun getAssets(): Response<List<Asset>>
    
    @GET("api/assets/{id}")
    suspend fun getAsset(@Path("id") id: Long): Response<Asset>
    
    @POST("api/assets")
    suspend fun createAsset(@Body assetRequest: AssetRequest): Response<Asset>
    
    @PUT("api/assets/{id}")
    suspend fun updateAsset(@Path("id") id: Long, @Body assetRequest: AssetRequest): Response<Asset>
    
    @DELETE("api/assets/{id}")
    suspend fun deleteAsset(@Path("id") id: Long): Response<Unit>
    
    // Detail Assets
    @GET("api/detail-assets")
    suspend fun getDetailAssets(): Response<List<DetailAsset>>
    
    @GET("api/detail-assets/{id}")
    suspend fun getDetailAsset(@Path("id") id: Long): Response<DetailAsset>
    
    @POST("api/detail-assets")
    suspend fun createDetailAsset(@Body detailAssetRequest: DetailAssetRequest): Response<DetailAsset>
    
    @PUT("api/detail-assets/{id}")
    suspend fun updateDetailAsset(@Path("id") id: Long, @Body detailAssetRequest: DetailAssetRequest): Response<DetailAsset>
    
    @DELETE("api/detail-assets/{id}")
    suspend fun deleteDetailAsset(@Path("id") id: Long): Response<Unit>
    
    // Projects
    @GET("api/projects")
    suspend fun getProjects(): Response<List<Project>>
    
    @GET("api/projects/{id}")
    suspend fun getProject(@Path("id") id: Long): Response<Project>
    
    @POST("api/projects")
    suspend fun createProject(@Body projectRequest: ProjectRequest): Response<Project>
    
    @PUT("api/projects/{id}")
    suspend fun updateProject(@Path("id") id: Long, @Body projectRequest: ProjectRequest): Response<Project>
    
    @DELETE("api/projects/{id}")
    suspend fun deleteProject(@Path("id") id: Long): Response<Unit>
    
    // Pengiriman
    @GET("api/pengiriman")
    suspend fun getPengiriman(): Response<List<Pengiriman>>
    
    @GET("api/pengiriman/{id}")
    suspend fun getPengirimanById(@Path("id") id: Long): Response<Pengiriman>
    
    @POST("api/pengiriman")
    suspend fun createPengiriman(@Body pengirimanRequest: PengirimanRequest): Response<Pengiriman>
    
    @PUT("api/pengiriman/{id}")
    suspend fun updatePengiriman(@Path("id") id: Long, @Body pengirimanRequest: PengirimanRequest): Response<Pengiriman>
    
    @DELETE("api/pengiriman/{id}")
    suspend fun deletePengiriman(@Path("id") id: Long): Response<Unit>
    
    // Tagihan
    @GET("api/tagihan")
    suspend fun getTagihan(): Response<List<Tagihan>>
    
    @GET("api/tagihan/{id}")
    suspend fun getTagihanById(@Path("id") id: Long): Response<Tagihan>
    
    @POST("api/tagihan")
    suspend fun createTagihan(@Body tagihanRequest: TagihanRequest): Response<Tagihan>
    
    @PUT("api/tagihan/{id}")
    suspend fun updateTagihan(@Path("id") id: Long, @Body tagihanRequest: TagihanRequest): Response<Tagihan>
    
    @DELETE("api/tagihan/{id}")
    suspend fun deleteTagihan(@Path("id") id: Long): Response<Unit>
    
    // Rental
    @GET("api/rental")
    suspend fun getRental(): Response<List<Rental>>
    
    @GET("api/rental/{id}")
    suspend fun getRentalById(@Path("id") id: Long): Response<Rental>
    
    @POST("api/rental")
    suspend fun createRental(@Body rentalRequest: RentalRequest): Response<Rental>
    
    @PUT("api/rental/{id}")
    suspend fun updateRental(@Path("id") id: Long, @Body rentalRequest: RentalRequest): Response<Rental>
    
    @DELETE("api/rental/{id}")
    suspend fun deleteRental(@Path("id") id: Long): Response<Unit>
} 
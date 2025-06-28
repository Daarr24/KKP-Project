package com.example.kkp.repository

import com.example.kkp.api.NetworkModule
import com.example.kkp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetRepository {
    
    suspend fun getAssets(): Result<List<Asset>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getAssets()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch assets: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getAsset(id: Long): Result<Asset> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getAsset(id)
                if (response.isSuccessful) {
                    val asset = response.body()
                    if (asset != null) {
                        Result.success(asset)
                    } else {
                        Result.failure(Exception("Asset not found"))
                    }
                } else {
                    Result.failure(Exception("Failed to fetch asset: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun createAsset(assetRequest: AssetRequest): Result<Asset> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.createAsset(assetRequest)
                if (response.isSuccessful) {
                    val asset = response.body()
                    if (asset != null) {
                        Result.success(asset)
                    } else {
                        Result.failure(Exception("Failed to create asset"))
                    }
                } else {
                    Result.failure(Exception("Failed to create asset: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun updateAsset(id: Long, assetRequest: AssetRequest): Result<Asset> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.updateAsset(id, assetRequest)
                if (response.isSuccessful) {
                    val asset = response.body()
                    if (asset != null) {
                        Result.success(asset)
                    } else {
                        Result.failure(Exception("Failed to update asset"))
                    }
                } else {
                    Result.failure(Exception("Failed to update asset: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun deleteAsset(id: Long): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.deleteAsset(id)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to delete asset: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    // Detail Asset operations
    suspend fun getDetailAssets(): Result<List<DetailAsset>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getDetailAssets()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch detail assets: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun createDetailAsset(detailAssetRequest: DetailAssetRequest): Result<DetailAsset> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.createDetailAsset(detailAssetRequest)
                if (response.isSuccessful) {
                    val detailAsset = response.body()
                    if (detailAsset != null) {
                        Result.success(detailAsset)
                    } else {
                        Result.failure(Exception("Failed to create detail asset"))
                    }
                } else {
                    Result.failure(Exception("Failed to create detail asset: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 
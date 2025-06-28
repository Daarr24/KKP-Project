package com.example.kkp.repository

import com.example.kkp.api.NetworkModule
import com.example.kkp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RentalRepository {
    
    // Rental operations
    suspend fun getRentals(): Result<List<Rental>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getRental()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch rentals: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getRental(id: Long): Result<Rental> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getRentalById(id)
                if (response.isSuccessful) {
                    val rental = response.body()
                    if (rental != null) {
                        Result.success(rental)
                    } else {
                        Result.failure(Exception("Rental not found"))
                    }
                } else {
                    Result.failure(Exception("Failed to fetch rental: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun createRental(rentalRequest: RentalRequest): Result<Rental> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.createRental(rentalRequest)
                if (response.isSuccessful) {
                    val rental = response.body()
                    if (rental != null) {
                        Result.success(rental)
                    } else {
                        Result.failure(Exception("Failed to create rental"))
                    }
                } else {
                    Result.failure(Exception("Failed to create rental: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun updateRental(id: Long, rentalRequest: RentalRequest): Result<Rental> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.updateRental(id, rentalRequest)
                if (response.isSuccessful) {
                    val rental = response.body()
                    if (rental != null) {
                        Result.success(rental)
                    } else {
                        Result.failure(Exception("Failed to update rental"))
                    }
                } else {
                    Result.failure(Exception("Failed to update rental: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun deleteRental(id: Long): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.deleteRental(id)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to delete rental: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    // Pengiriman operations
    suspend fun getPengiriman(): Result<List<Pengiriman>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getPengiriman()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch pengiriman: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun createPengiriman(pengirimanRequest: PengirimanRequest): Result<Pengiriman> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.createPengiriman(pengirimanRequest)
                if (response.isSuccessful) {
                    val pengiriman = response.body()
                    if (pengiriman != null) {
                        Result.success(pengiriman)
                    } else {
                        Result.failure(Exception("Failed to create pengiriman"))
                    }
                } else {
                    Result.failure(Exception("Failed to create pengiriman: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    // Tagihan operations
    suspend fun getTagihan(): Result<List<Tagihan>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getTagihan()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch tagihan: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun createTagihan(tagihanRequest: TagihanRequest): Result<Tagihan> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.createTagihan(tagihanRequest)
                if (response.isSuccessful) {
                    val tagihan = response.body()
                    if (tagihan != null) {
                        Result.success(tagihan)
                    } else {
                        Result.failure(Exception("Failed to create tagihan"))
                    }
                } else {
                    Result.failure(Exception("Failed to create tagihan: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    // Project operations
    suspend fun getProjects(): Result<List<Project>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NetworkModule.apiService.getProjects()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Failed to fetch projects: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 
package com.example.kkp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kkp.model.*
import com.example.kkp.repository.AssetRepository
import com.example.kkp.model.AssetListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssetViewModel : ViewModel() {
    
    private val assetRepository = AssetRepository()
    
    private val _assets = MutableStateFlow<List<Asset>>(emptyList())
    val assets: StateFlow<List<Asset>> = _assets.asStateFlow()
    
    private val _detailAssets = MutableStateFlow<List<DetailAsset>>(emptyList())
    val detailAssets: StateFlow<List<DetailAsset>> = _detailAssets.asStateFlow()
    
    private val _selectedAsset = MutableStateFlow<Asset?>(null)
    val selectedAsset: StateFlow<Asset?> = _selectedAsset.asStateFlow()
    
    private val _assetState = MutableStateFlow<AssetState>(AssetState.Idle)
    val assetState: StateFlow<AssetState> = _assetState.asStateFlow()
    
    init {
        loadAssets()
        loadDetailAssets()
    }
    
    fun loadAssets() {
        viewModelScope.launch {
            _assetState.value = AssetState.Loading
            
            val result = assetRepository.getAssets()
            result.fold(
                onSuccess = { assetsList ->
                    _assets.value = assetsList
                    _assetState.value = AssetState.Success
                },
                onFailure = { exception ->
                    _assetState.value = AssetState.Error(exception.message ?: "Failed to load assets")
                }
            )
        }
    }
    
    fun loadDetailAssets() {
        viewModelScope.launch {
            val result = assetRepository.getDetailAssets()
            result.fold(
                onSuccess = { detailAssetsList ->
                    _detailAssets.value = detailAssetsList
                },
                onFailure = { exception ->
                    // Handle error silently for now
                }
            )
        }
    }
    
    fun selectAsset(asset: Asset) {
        _selectedAsset.value = asset
    }
    
    fun createAsset(merk: String, type: String, spesifikasi: String) {
        viewModelScope.launch {
            _assetState.value = AssetState.Loading
            
            val assetRequest = AssetRequest(merk, type, spesifikasi)
            val result = assetRepository.createAsset(assetRequest)
            result.fold(
                onSuccess = { asset ->
                    _assets.value = _assets.value + asset
                    _assetState.value = AssetState.Success
                },
                onFailure = { exception ->
                    _assetState.value = AssetState.Error(exception.message ?: "Failed to create asset")
                }
            )
        }
    }
    
    fun createDetailAsset(assetId: Long, serialNumber: String, kondisi: String) {
        viewModelScope.launch {
            val detailAssetRequest = DetailAssetRequest(assetId, serialNumber, kondisi)
            val result = assetRepository.createDetailAsset(detailAssetRequest)
            result.fold(
                onSuccess = { detailAsset ->
                    _detailAssets.value = _detailAssets.value + detailAsset
                },
                onFailure = { exception ->
                    // Handle error
                }
            )
        }
    }
    
    fun resetState() {
        _assetState.value = AssetState.Idle
    }
}

sealed class AssetState {
    object Idle : AssetState()
    object Loading : AssetState()
    object Success : AssetState()
    data class Error(val message: String) : AssetState()
} 
 
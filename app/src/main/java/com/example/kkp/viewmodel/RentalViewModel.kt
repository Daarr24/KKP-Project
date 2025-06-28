package com.example.kkp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kkp.model.*
import com.example.kkp.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RentalViewModel : ViewModel() {
    
    private val rentalRepository = RentalRepository()
    
    private val _rentals = MutableStateFlow<List<Rental>>(emptyList())
    val rentals: StateFlow<List<Rental>> = _rentals.asStateFlow()
    
    private val _pengiriman = MutableStateFlow<List<Pengiriman>>(emptyList())
    val pengiriman: StateFlow<List<Pengiriman>> = _pengiriman.asStateFlow()
    
    private val _tagihan = MutableStateFlow<List<Tagihan>>(emptyList())
    val tagihan: StateFlow<List<Tagihan>> = _tagihan.asStateFlow()
    
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()
    
    private val _selectedRental = MutableStateFlow<Rental?>(null)
    val selectedRental: StateFlow<Rental?> = _selectedRental.asStateFlow()
    
    private val _rentalState = MutableStateFlow<RentalState>(RentalState.Idle)
    val rentalState: StateFlow<RentalState> = _rentalState.asStateFlow()
    
    // Statistics for dashboard cards
    private val _activeRentals = MutableStateFlow(0)
    val activeRentals: StateFlow<Int> = _activeRentals.asStateFlow()
    
    private val _upcomingRentals = MutableStateFlow(0)
    val upcomingRentals: StateFlow<Int> = _upcomingRentals.asStateFlow()
    
    private val _returnRentals = MutableStateFlow(0)
    val returnRentals: StateFlow<Int> = _returnRentals.asStateFlow()
    
    init {
        loadRentals()
        loadPengiriman()
        loadTagihan()
        loadProjects()
    }
    
    fun loadRentals() {
        viewModelScope.launch {
            _rentalState.value = RentalState.Loading
            
            val result = rentalRepository.getRentals()
            result.fold(
                onSuccess = { rentalsList ->
                    _rentals.value = rentalsList
                    calculateStatistics(rentalsList)
                    _rentalState.value = RentalState.Success
                },
                onFailure = { exception ->
                    _rentalState.value = RentalState.Error(exception.message ?: "Failed to load rentals")
                }
            )
        }
    }
    
    fun loadPengiriman() {
        viewModelScope.launch {
            val result = rentalRepository.getPengiriman()
            result.fold(
                onSuccess = { pengirimanList ->
                    _pengiriman.value = pengirimanList
                },
                onFailure = { exception ->
                    // Handle error silently for now
                }
            )
        }
    }
    
    fun loadTagihan() {
        viewModelScope.launch {
            val result = rentalRepository.getTagihan()
            result.fold(
                onSuccess = { tagihanList ->
                    _tagihan.value = tagihanList
                },
                onFailure = { exception ->
                    // Handle error silently for now
                }
            )
        }
    }
    
    fun loadProjects() {
        viewModelScope.launch {
            val result = rentalRepository.getProjects()
            result.fold(
                onSuccess = { projectsList ->
                    _projects.value = projectsList
                },
                onFailure = { exception ->
                    // Handle error silently for now
                }
            )
        }
    }
    
    private fun calculateStatistics(rentals: List<Rental>) {
        val active = rentals.count { it.status == "aktif" }
        val upcoming = rentals.count { it.status == "upcoming" }
        val returned = rentals.count { it.status == "selesai" }
        
        _activeRentals.value = active
        _upcomingRentals.value = upcoming
        _returnRentals.value = returned
    }
    
    fun selectRental(rental: Rental) {
        _selectedRental.value = rental
    }
    
    fun createRental(
        pengirimanId: Long,
        projectId: Long,
        status: String,
        periodeMulai: String,
        periodeAkhir: String,
        totalTagihan: Int
    ) {
        viewModelScope.launch {
            _rentalState.value = RentalState.Loading
            val rentalRequest = RentalRequest(
                pengirimanId = pengirimanId,
                projectId = projectId,
                status = status,
                periodeMulai = periodeMulai,
                periodeAkhir = periodeAkhir,
                totalTagihan = totalTagihan
            )
            val result = rentalRepository.createRental(rentalRequest)
            result.fold(
                onSuccess = { rental ->
                    _rentals.value = _rentals.value + rental
                    calculateStatistics(_rentals.value)
                    _rentalState.value = RentalState.Success
                },
                onFailure = { exception ->
                    _rentalState.value = RentalState.Error(exception.message ?: "Failed to create rental")
                }
            )
        }
    }
    
    fun createPengiriman(
        detailAssetId: Long,
        tanggalPengiriman: String,
        picPengirim: String,
        picPenerima: String
    ) {
        viewModelScope.launch {
            val pengirimanRequest = PengirimanRequest(
                detailAssetId = detailAssetId,
                tanggalPengiriman = tanggalPengiriman,
                picPengirim = picPengirim,
                picPenerima = picPenerima
            )
            
            val result = rentalRepository.createPengiriman(pengirimanRequest)
            result.fold(
                onSuccess = { pengiriman ->
                    _pengiriman.value = _pengiriman.value + pengiriman
                },
                onFailure = { exception ->
                    // Handle error
                }
            )
        }
    }
    
    fun createTagihan(
        rentalId: Long,
        nomorInvoice: Int,
        keterangan: String,
        tanggalTagihan: String,
        jumlahUnit: Int,
        durasiTagih: String,
        grandTotal: Int
    ) {
        viewModelScope.launch {
            val tagihanRequest = TagihanRequest(
                rentalId = rentalId,
                nomorInvoice = nomorInvoice,
                keterangan = keterangan,
                tanggalTagihan = tanggalTagihan,
                jumlahUnit = jumlahUnit,
                durasiTagih = durasiTagih,
                grandTotal = grandTotal
            )
            val result = rentalRepository.createTagihan(tagihanRequest)
            result.fold(
                onSuccess = { tagihan ->
                    _tagihan.value = _tagihan.value + tagihan
                },
                onFailure = { exception ->
                    // Handle error
                }
            )
        }
    }
    
    fun resetState() {
        _rentalState.value = RentalState.Idle
    }
}

sealed class RentalState {
    object Idle : RentalState()
    object Loading : RentalState()
    object Success : RentalState()
    data class Error(val message: String) : RentalState()
} 
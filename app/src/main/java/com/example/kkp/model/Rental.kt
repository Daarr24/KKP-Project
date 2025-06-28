package com.example.kkp.model

import com.google.gson.annotations.SerializedName

data class Pengiriman(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("detailasset_id")
    val detailAssetId: Long,
    
    @SerializedName("tanggal_pengiriman")
    val tanggalPengiriman: String,
    
    @SerializedName("pic_pengirim")
    val picPengirim: String,
    
    @SerializedName("pic_penerima")
    val picPenerima: String,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?,
    
    @SerializedName("detail_asset")
    val detailAsset: DetailAsset? = null
)

data class Tagihan(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("rental_id")
    val rentalId: Long,
    
    @SerializedName("nomor_invoice")
    val nomorInvoice: Int,
    
    @SerializedName("keterangan")
    val keterangan: String,
    
    @SerializedName("tanggal_tagihan")
    val tanggalTagihan: String,
    
    @SerializedName("jumlah_unit")
    val jumlahUnit: Int,
    
    @SerializedName("durasi_tagih")
    val durasiTagih: String,
    
    @SerializedName("grand_total")
    val grandTotal: Int,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?,
    
    @SerializedName("rental")
    val rental: Rental? = null
)

data class Rental(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("pengiriman_id")
    val pengirimanId: Long,
    
    @SerializedName("project_id")
    val projectId: Long,
    
    @SerializedName("status")
    val status: String, // aktif
    
    @SerializedName("periode_mulai")
    val periodeMulai: String,
    
    @SerializedName("periode_ahir")
    val periodeAkhir: String,
    
    @SerializedName("total_tagihan")
    val totalTagihan: Int,
    
    @SerializedName("created_at")
    val createdAt: String?,
    
    @SerializedName("updated_at")
    val updatedAt: String?,
    
    @SerializedName("pengiriman")
    val pengiriman: Pengiriman? = null,
    
    @SerializedName("project")
    val project: Project? = null,
    
    @SerializedName("tagihan")
    val tagihan: Tagihan? = null
)

data class PengirimanRequest(
    @SerializedName("detailasset_id")
    val detailAssetId: Long,
    
    @SerializedName("tanggal_pengiriman")
    val tanggalPengiriman: String,
    
    @SerializedName("pic_pengirim")
    val picPengirim: String,
    
    @SerializedName("pic_penerima")
    val picPenerima: String
)

data class TagihanRequest(
    @SerializedName("rental_id")
    val rentalId: Long,
    
    @SerializedName("nomor_invoice")
    val nomorInvoice: Int,
    
    @SerializedName("keterangan")
    val keterangan: String,
    
    @SerializedName("tanggal_tagihan")
    val tanggalTagihan: String,
    
    @SerializedName("jumlah_unit")
    val jumlahUnit: Int,
    
    @SerializedName("durasi_tagih")
    val durasiTagih: String,
    
    @SerializedName("grand_total")
    val grandTotal: Int
)

data class RentalRequest(
    @SerializedName("pengiriman_id")
    val pengirimanId: Long,
    
    @SerializedName("project_id")
    val projectId: Long,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("periode_mulai")
    val periodeMulai: String,
    
    @SerializedName("periode_ahir")
    val periodeAkhir: String,
    
    @SerializedName("total_tagihan")
    val totalTagihan: Int
) 
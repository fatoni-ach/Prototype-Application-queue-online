package com.example.prototypeantrianonline.modul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pasien(val nama_pasien1:String,
             var jenis_kelamin1: String,
             var umur1: Int,
             var nama_dokter1: String,
             var jenis_pengobatan1: String,
             var waktu_mulai1: String,
             var waktu_berakhir1: String,
             var durasi_pengobatan1: Int
             ) {
    @SerializedName("nama_pasien")
    @Expose
    var nama_pasien: String? = nama_pasien1

    @SerializedName("jenis_kelamin")
    @Expose
    var jenis_kelamin: String? = jenis_kelamin1

    @SerializedName("umur")
    @Expose
    var umur: Int? = umur1

    @SerializedName("nama_dokter")
    @Expose
    var nama_dokter: String? = nama_dokter1

    @SerializedName("jenis_pengobatan")
    @Expose
    var jenis_pengobatan: String? = jenis_pengobatan1

    @SerializedName("waktu_mulai")
    @Expose
    var waktu_mulai: String? = waktu_mulai1

    @SerializedName("waktu_berakhir")
    @Expose
    var waktu_berakhir: String? = waktu_berakhir1

    @SerializedName("durasi_pengobatan")
    @Expose
    var durasi_pengobatan: Int? = durasi_pengobatan1
}
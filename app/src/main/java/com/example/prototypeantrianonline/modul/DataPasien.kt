package com.example.prototypeantrianonline.modul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataPasien(var nama_pasien1: String,
                 var no_ktp1: String,
                 var jenis_kelamin1: String,
                 var tempat_tgl_lahir1: String,
                 var Status1: String,
                 var gol_darah1: String,
                 var no_telp1: String,
                 var alamat1: String


) {

    @SerializedName("nama_pasien")
    @Expose
    var nama_pasien: String? = nama_pasien1

    @SerializedName("no_ktp")
    @Expose
    var no_ktp: String? = no_ktp1

    @SerializedName("jenis_kelamin")
    @Expose
    var jenis_kelamin: String? = jenis_kelamin1

    @SerializedName("tempat_tgl_lahir")
    @Expose
    var tempat_tgl_lahir: String? = tempat_tgl_lahir1

    @SerializedName("Status")
    @Expose
    var Status: String? = Status1

    @SerializedName("gol_darah")
    @Expose
    var gol_darah: String? = gol_darah1

    @SerializedName("no_telp")
    @Expose
    var no_telp: String? = no_telp1

    @SerializedName("alamat")
    @Expose
    var alamat: String? = alamat1

}
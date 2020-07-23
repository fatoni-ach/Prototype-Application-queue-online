package com.example.prototypeantrianonline.modul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatusAntrian(val no1:Int,
                    val waktu_tunggu1:String,
                    val jumlah_antrian1:Int,
                    val jam_dipanggil1:String) {
    @SerializedName("no")
    @Expose
    var no: Int? = no1

    @SerializedName("waktu_tunggu")
    @Expose
    var waktu_tunggu: String? = waktu_tunggu1

    @SerializedName("jam_dipanggil")
    @Expose
    var jam_dipanggil: String? = jam_dipanggil1

    @SerializedName("jumlah_antrian")
    @Expose
    var jumlah_antrian: Int? = jumlah_antrian1
}
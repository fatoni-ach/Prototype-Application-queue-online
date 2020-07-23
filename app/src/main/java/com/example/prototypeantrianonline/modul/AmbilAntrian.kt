package com.example.prototypeantrianonline.modul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AmbilAntrian(var no_telp1: String,
                   var jenis_pengobatan1: String) {
    @SerializedName("no_telp")
    @Expose
    var no_telp: String? = no_telp1

    @SerializedName("jenis_pengobatan")
    @Expose
    var jenis_pengobatan: String? = jenis_pengobatan1
}
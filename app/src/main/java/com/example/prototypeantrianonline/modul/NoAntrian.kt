package com.example.prototypeantrianonline.modul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NoAntrian( var no1: Int,
                 var durasi1: Int,
                 var data_pasien1: DataPasien,
                 var status1: String,
                 var pemanggil1: String
                 ) {
    @SerializedName("no")
    @Expose
    var no: Int? = no1

    @SerializedName("durasi")
    @Expose
    var durasi: Int? = durasi1

    @SerializedName("data_pasien")
    @Expose
    var data_pasien: DataPasien? = data_pasien1

    @SerializedName("status")
    @Expose
    var status: String? = status1

    @SerializedName("pemanggil")
    @Expose
    var pemanggil: String? = pemanggil1
}
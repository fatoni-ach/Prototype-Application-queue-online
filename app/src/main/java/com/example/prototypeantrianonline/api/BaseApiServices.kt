package com.example.prototypeantrianonline.api

import com.example.prototypeantrianonline.modul.ReadDataResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BaseApiServices {
    @FormUrlEncoded
    @POST("api/request")
    fun readData(
        @Field("action") action: String?,
        @Field("tabelname") tabelname: String?
    ): Call<ReadDataResponse?>?
}
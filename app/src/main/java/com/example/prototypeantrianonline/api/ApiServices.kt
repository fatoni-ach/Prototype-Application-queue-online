package com.example.prototypeantrianonline.api

import com.example.prototypeantrianonline.modul.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("api/request/list/")
    fun getPasien(): Call<List<Pasien>>

    @POST("api/request/list/")
    fun getPost(@Body dataPasien: DataPasien): Call<DataPasien>

    @POST("api/request/datapasien/")
    fun getDataPasien (@Body dataPasien: DataPasien): Call<DataPasien>

    @POST("api/request/adddatapasien/")
    fun addDataPasien (@Body dataPasien: DataPasien): Call<String>

    @POST("api/request/addnoantrian/")
    fun addNoAntrian (@Body ambilAntrian: AmbilAntrian): Call<NoAntrian>

    //private
    @FormUrlEncoded
    @POST("api/request/getStatus/")
    fun getAntrianStatus(@Field("action") action: String,
                         @Field("no_telp") no_telp: String,
                         @Field("status") status:String,
                         @Field("jenis_pengobatan") jenis_pengobatan: String): Call<StatusAntrian>
    //umum
    @FormUrlEncoded
    @POST("api/request/getStatus/")
    fun getAntrianStatusUmum(@Field("action")action: String,
                             @Field("jenis_pengobatan")jenis_pengobatan:String): Call<StatusAntrian>

    @FormUrlEncoded
    @POST("api/request/getDokterShift/")
    fun getStatusShift(@Field("jenis_pengobatan")action: String): Call<String>

    /////////////////////////////////////////////
    @POST("api/request/list/")
    @FormUrlEncoded
    fun cekNoTelp(
        @Header ("csrfmiddlewaretoken")  csrfmiddlewaretoken:String,
        @Field ("action")  action:String,
        @Field ("no_telp")  no_telp:String
                  ): Call<ReadDataResponse>


    @POST("api/request/")
    @FormUrlEncoded
    fun getAntrian(@Query ("action")  action:String, @Query ("no_telp")  no_telp:String ): Call<ReadDataResponse>
}
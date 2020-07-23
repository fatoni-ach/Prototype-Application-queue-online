package com.example.prototypeantrianonline.api

import android.app.Application
import com.example.prototypeantrianonline.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiMain : Application() {
    public val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }).readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    public val retrofit = Retrofit.Builder()
        .baseUrl("http://mqueue.herokuapp.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val services: ApiServices =
        retrofit.create(ApiServices::class.java)
}

//class ApiMain : Application() {
//    public val client = OkHttpClient().newBuilder()
//        .addInterceptor(
//
//
//        ).readTimeout(30, TimeUnit.SECONDS)
//        .writeTimeout(30, TimeUnit.SECONDS)
//        .build()
//
//    public val retrofit = Retrofit.Builder()
//        .baseUrl("http://mqueue.herokuapp.com/")
//        .client(client)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val services: ApiServices =
//        retrofit.create(ApiServices::class.java)
//}
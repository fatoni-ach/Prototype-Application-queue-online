package com.example.prototypeantrianonline.api

import retrofit2.create

class UtilsAPI {
    val BASE_ROOT_URL = "mqueue.herokuapp.com"
    fun getApiService(): BaseApiServices? {
        return RetrofitClient.getClient (BASE_ROOT_URL)
            ?.create(
                BaseApiServices::class.java
            )
    }
}
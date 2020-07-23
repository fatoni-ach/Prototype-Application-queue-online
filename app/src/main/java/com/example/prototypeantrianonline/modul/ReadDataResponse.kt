package com.example.prototypeantrianonline.modul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ReadDataResponse {
    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("prediction")
    @Expose
    var prediction: String? = null

    @SerializedName("confidence_score")
    @Expose
    var confidenceScore: Double? = null

    @SerializedName("action")
    @Expose
    var action: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("no_antrian")
    @Expose
    var no_antrian: String? = null

}
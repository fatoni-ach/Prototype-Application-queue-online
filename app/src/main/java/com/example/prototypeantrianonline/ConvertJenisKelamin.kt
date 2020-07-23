package com.example.prototypeantrianonline

import android.content.Context
import kotlinx.android.synthetic.main.activity_pendaftaran.*

class ConvertJenisKelamin(val jenis_kelamin:String) {
    fun getJenisKelamin(context: Context):String{
        val jp:String
        if (jenis_kelamin == "Laki-laki"){
            jp = "l"
        }else
            jp = "p"
        return jp
    }
}
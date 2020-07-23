package com.example.prototypeantrianonline

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.prototypeantrianonline.api.ApiMain
import com.example.prototypeantrianonline.modul.StatusAntrian
import com.example.prototypeantrianonline.room.DataLokalDatabase
import kotlinx.android.synthetic.main.activity_lihat_antrian.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LihatAntrian : AppCompatActivity() {
    val apimain = ApiMain()
    val db = DataLokalDatabase(this)
    val status1 = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_antrian)

        val nul = "-"
        TV_no_antrian_LA.text   = nul
        TV_LA_durasi.text       = nul
        TV_LA_antrian.text      = nul
        TV_LA_NAA.text          = "Nomor Antrian Anda"
        TV_LA_poli.text         = nul
        TV_LA_prediksi_jam.text = nul

        GlobalScope.launch {
            val data = db.dataAntrianDao().getCount()
            if (data != 0){
                val data1 = db.dataAntrianDao().getAll()
                val no_telp = data1[0].no_telp
                val status = data1[0].status
                val jenis_pengobatan = data1[0].jenis_pengobatan
                getStatus(no_telp, status, jenis_pengobatan)
                Log.d("DATABASE : ", data.toString())
            } else{
                getId()
            }
        }
    }

    fun BT_home(view: View) {
//        getId()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
    }

    fun getId(){
        TV_LA_NAA.text = "Anda Belum mengambil antrian"
        TV_LA_NAA.setTextColor(ContextCompat.getColor(
            baseContext, R.color.colorButtonPrimary))
    }

    fun getStatus(no_telp:String, status:String, jenis_pengobatan:String){
        apimain.services.getAntrianStatus("private",no_telp, status, jenis_pengobatan).enqueue(object :
            Callback<StatusAntrian> {
            override fun onResponse(call: Call<StatusAntrian>, response: Response<StatusAntrian>) {
                if(response.isSuccessful){
                    val status: StatusAntrian = response.body()!!
                    if (status.waktu_tunggu!="null"){
                        TV_no_antrian_LA.text   = status.no.toString()
                        TV_LA_durasi.text     = status.waktu_tunggu.toString()
                        TV_LA_antrian.text      = status.jumlah_antrian.toString()
                        TV_LA_poli.text         = jenis_pengobatan.toString()
                        TV_LA_prediksi_jam.text = status.jam_dipanggil.toString()
                    }else{
                        breakTable()
                        getId()
                    }
                }
            }

            override fun onFailure(call: Call<StatusAntrian>, t: Throwable) {
                Log.d("response status:", t.message.toString())
                Toast.makeText(applicationContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun breakTable(){
        GlobalScope.launch {
            db.dataAntrianDao().deleteAll()
            val data = db.dataAntrianDao().getCount()
            Log.d("DATABASE BREAK ", data.toString())
        }
    }
}

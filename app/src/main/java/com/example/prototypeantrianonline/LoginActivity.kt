package com.example.prototypeantrianonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.example.prototypeantrianonline.api.ApiMain
import com.example.prototypeantrianonline.modul.DataPasien
import com.example.prototypeantrianonline.modul.ReadDataResponse
import com.example.prototypeantrianonline.modul.TokenResponse
import com.example.prototypeantrianonline.room.DataAntrianEntity
import com.example.prototypeantrianonline.room.DataLokalDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val apimain = ApiMain()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


//        ArrayAdapter.createFromResource(this,
//            R.array.jenis_pengobatan_array,
//            android.R.layout.simple_spinner_item)
//            .also {
//                adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                SP_L_jenis_pengobatan.adapter = adapter
//            }

        BT_L_next.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (ET_L_no_telp.text!!.length >= 11) {
                    request_api()
//                BT_L_next.isVisible = false
                    disable(BT_L_next, PB_L_load)
                } else {
                    ET_L_no_telp.error = "Nomor telepon min 11 angka"
                }
            }
        })
    }

    fun request_api(){
        val dataPasien :DataPasien = DataPasien(
            "n",
            "n",
            "l",
            "n",
            "n",
            "A",
            ET_L_no_telp.text.toString(),
            "n"
        )
        apimain.services.getDataPasien(dataPasien).enqueue(object :
        Callback<DataPasien>{
            override fun onResponse(call: Call<DataPasien>, response: Response<DataPasien>) {
                if(response.isSuccessful){
                    val data :DataPasien = response.body()!!
                    if (data.nama_pasien == "n"){
                        val intent :Intent = Intent(baseContext, PendaftaranActivity::class.java)
                        intent.putExtra("no_telp", ET_L_no_telp.text.toString())
                        startActivity(intent)
                    }else{
                        val intent :Intent = Intent(baseContext, AmbilAntrianActivity::class.java)
                        intent.putExtra("no_telp", ET_L_no_telp.text.toString())
                        startActivity(intent)
                    }
                }
                enable(BT_L_next, PB_L_load)
            }
            override fun onFailure(call: Call<DataPasien>, t: Throwable) {
                Log.d("response", t.message.toString())
                Toast.makeText(applicationContext, "Koneksi sedang bermasalah", Toast.LENGTH_SHORT).show()
                enable(BT_L_next, PB_L_load)
            }
        })
    }

//    fun save(status:String, no_antrian:Int){
//        val db = DataLokalDatabase(this)
//        GlobalScope.launch {
//            db.dataAntrianDao().insertAll(DataAntrianEntity(0, ET_L_no_telp.text.toString(), "uncall",
//                no_antrian
//            ))
//            val data = db.dataAntrianDao().getAll()
//            data?.forEach{
//                Log.d("Database Lokal : ", it.toString())
//            }
//        }
//    }
    fun disable(button: Button, progressBar: ProgressBar){
        button.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
    fun enable(button: Button, progressBar: ProgressBar){
        button.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

}



package com.example.prototypeantrianonline

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.prototypeantrianonline.api.ApiMain
import com.example.prototypeantrianonline.modul.DataPasien
import com.example.prototypeantrianonline.modul.Pasien
import com.example.prototypeantrianonline.modul.StatusAntrian
import com.example.prototypeantrianonline.room.DataAntrianDao
import com.example.prototypeantrianonline.room.DataAntrianEntity
import com.example.prototypeantrianonline.room.DataLokalDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var bt_ambil_antrian: Button? = null;
    val apimain = ApiMain()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DataLokalDatabase(this)
        val BT_HM_ambil1 = findViewById<Button>(R.id.BT_HM_ambil)

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),1)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_NETWORK_STATE),1)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_WIFI_STATE),1)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CHANGE_NETWORK_STATE),1)
//        }

//        GlobalScope.launch{
////            db.dataAntrianDao().insertAll(DataAntrianEntity(0, "087", "uncall", 20))
////
//            val data = db.dataAntrianDao().getAll()
//
//            data?.forEach{
//                Log.d("DATABASE : ", it.toString())
//            }
//            Log.d("DATABASE : ", "None")
//
//        }
        textviewNone()
//        getStatus()

        BT_HM_ambil1.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
        })

        BT_lihat_antrian.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                startActivity(Intent(applicationContext, LihatAntrian::class.java))
            }
        })

        GlobalScope.launch {
            val data = db.dataAntrianDao().getCount()
            Log.d("DATABASE LOKAL :", data.toString())
            if (data==0){
                enableButton(BT_HM_ambil1)
            } else {
                disableButton(BT_HM_ambil1)
                val data1 = db.dataAntrianDao().getAll()
                val no_telp = data1[0].no_telp
                val status = data1[0].status
                val jp = data1[0].jenis_pengobatan
                getStatus(no_telp,status, jp)
            }
        }
        SP_HOME_jp?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                textviewNone()
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var jp :String
                jp = ""
                if(selectedItem == "Penyakit dalam"){
                    jp = "Penyakit dalam"
                }else if (selectedItem == "spesialis saraf"){
                    jp = "spesialis saraf"
                }else if (selectedItem == "klinik bedah"){
                    jp = "klinik bedah"
                }
                getStatus(jp)
//                Toast.makeText(applicationContext, jp.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getPost(){
        var dataPasien :DataPasien = DataPasien(
            "Ach Fatoni",
            "no_ktp008",
            "l",
            "bangkalan",
            "belum menikah",
            "O",
            "082332",
            "Rabasan"
        )

        apimain.services.getPost(dataPasien).enqueue(object :
        Callback<DataPasien>{
            override fun onResponse(
                call: Call<DataPasien>,
                response: Response<DataPasien>
            ) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val dataPasien:DataPasien
                        dataPasien = response.body()!!
                            Log.d("response : ", dataPasien.nama_pasien.toString())
                            Log.d("response : ", dataPasien.alamat.toString())
                    }
                }
            }

            override fun onFailure(call: Call<DataPasien>, t: Throwable) {
                Log.d("response : ", t.message.toString())
            }
        })
    }

    fun getPasien(){

        apimain.services.getPasien().enqueue(object :
            Callback<List<Pasien>>{
            override fun onResponse(call: Call<List<Pasien>>, response: Response<List<Pasien>>) {
//                TODO("Not yet implemented")
                if(response.isSuccessful){
                    if(response.body() != null){
                        val pasien:List<Pasien>
                        pasien = response.body()!!
                        for( h: Pasien in pasien){
                            Log.d("response : ", h.nama_pasien.toString())
                            Log.d("response : ", h.nama_dokter.toString())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Pasien>>, t: Throwable) {
                Log.d("response : ", "ERROR")
            }
        }
        )
    }

    fun getStatus(jp:String){
        apimain.services.getAntrianStatusUmum("umum", jp).enqueue(object :
        Callback<StatusAntrian>{
            override fun onResponse(call: Call<StatusAntrian>, response: Response<StatusAntrian>) {
                if (response.isSuccessful) {
                    val status: StatusAntrian = response.body()!!
                    TV_no_antrian_HM.text   = status.no.toString()
                    TV_HM_prediksi.text     = status.waktu_tunggu.toString()
                    TV_HM_antrian.text      = status.jumlah_antrian.toString()
                    TV_HM_prediksi_jam.text = status.jam_dipanggil.toString()
                }
            }

            override fun onFailure(call: Call<StatusAntrian>, t: Throwable) {
                Log.d("response status:", t.message.toString())
                Toast.makeText(applicationContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun disableButton(button: Button){
        button.isClickable = false
        button.isEnabled = true
        button.background = ContextCompat.getDrawable(baseContext, R.drawable.button_grey)
        button.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
    }

    fun enableButton(button: Button) {
        button.isClickable = true
        button.isEnabled = true
        button.background = ContextCompat.getDrawable(baseContext, R.drawable.button_primary)
        button.setTextColor(ContextCompat.getColor(baseContext, R.color.white))
    }

    fun getStatus(no_telp:String, status:String, jenis_pengobatan:String){
        apimain.services.getAntrianStatus("private",no_telp, status, jenis_pengobatan).enqueue(object :
            Callback<StatusAntrian> {
            override fun onResponse(call: Call<StatusAntrian>, response: Response<StatusAntrian>) {
                if(response.isSuccessful){
                    val status: StatusAntrian = response.body()!!
                    if (status.waktu_tunggu=="null"){
                        LihatAntrian().breakTable()
                        enableButton(BT_HM_ambil)
                    }
                }
            }

            override fun onFailure(call: Call<StatusAntrian>, t: Throwable) {
                Log.d("response status:", t.message.toString())
                Toast.makeText(applicationContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun textviewNone(){
        val nul = "-"
        TV_no_antrian_HM.text   = nul
        TV_HM_prediksi.text     = nul
        TV_HM_antrian.text      = nul
        TV_HM_prediksi_jam.text = nul
    }

}

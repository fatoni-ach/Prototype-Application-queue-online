package com.example.prototypeantrianonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.prototypeantrianonline.api.ApiMain
import com.example.prototypeantrianonline.modul.AmbilAntrian
import com.example.prototypeantrianonline.modul.NoAntrian
import com.example.prototypeantrianonline.room.DataAntrianEntity
import com.example.prototypeantrianonline.room.DataLokalDatabase
import kotlinx.android.synthetic.main.activity_ambil_antrian.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AmbilAntrianActivity : AppCompatActivity() {
    val apimain = ApiMain()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ambil_antrian)
        var no_telp:String = intent.getStringExtra("no_telp")

        BT_AA_ambil.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
//                Toast.makeText(applicationContext, no_telp, Toast.LENGTH_SHORT).show()
                StatusButton().disable(BT_AA_ambil, PB_AA_loading)
                request_api(no_telp)
            }
        })

        SP_AA_jenis_pengobatan?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TV_AA_dokter.text   = "-"
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
                getDokterShift(jp)

            }
        }
    }

    fun request_api(no_telp:String){
        val ambilAntrian:AmbilAntrian = AmbilAntrian(
            no_telp,
            SP_AA_jenis_pengobatan.selectedItem.toString())
        apimain.services.addNoAntrian(ambilAntrian).enqueue(object :
        Callback<NoAntrian>{
            override fun onResponse(call: Call<NoAntrian>, response: Response<NoAntrian>) {
                if(response.isSuccessful){
                    val noAntrian:NoAntrian = response.body()!!
                    save(noAntrian)
                    val intent:Intent = Intent(applicationContext, LihatAntrian::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                StatusButton().enable(BT_AA_ambil, PB_AA_loading)
            }

            override fun onFailure(call: Call<NoAntrian>, t: Throwable) {
                Log.d("error : ", t.message.toString())
                Toast.makeText(applicationContext, "SERVER DOWN !!", Toast.LENGTH_SHORT).show()
                StatusButton().enable(BT_AA_ambil, PB_AA_loading)
            }
        })
    }

    fun save(noAntrian:NoAntrian){
        val db = DataLokalDatabase(this)
        GlobalScope.launch{
        db.dataAntrianDao().insertAll(DataAntrianEntity(0, noAntrian.data_pasien?.no_telp.toString(),
            "uncall", noAntrian.no.toString().toInt(), SP_AA_jenis_pengobatan.selectedItem.toString()))

        val data = db.dataAntrianDao().getAll()

        data?.forEach{
            Log.d("DATABASE : ", it.toString())
        }
        }
    }

    fun getDokterShift( jp:String){
        apimain.services.getStatusShift(jp).enqueue(object :
        Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    TV_AA_dokter.text   = response.body().toString()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("response status:", t.message.toString())
                Toast.makeText(applicationContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

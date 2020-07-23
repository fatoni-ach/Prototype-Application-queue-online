package com.example.prototypeantrianonline

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prototypeantrianonline.api.ApiMain
import com.example.prototypeantrianonline.modul.DataPasien
import kotlinx.android.synthetic.main.activity_pendaftaran.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class PendaftaranActivity : AppCompatActivity() {
    val apimain = ApiMain()
//    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendaftaran)
        val myCalendar :Calendar = Calendar.getInstance()
        myCalendar.set(Calendar.YEAR, 1990)
        myCalendar.set(Calendar.MONTH, 0)
        myCalendar.set(Calendar.DAY_OF_MONTH, 1)

        val no_telp = intent.getStringExtra("no_telp")
        BT_PEN_ambil.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var status = true
                status = checkError()
                if (status){
                    BT_ambil_antrian(no_telp)
                    StatusButton().disable(BT_PEN_ambil, PB_PEN_loading)
                }
            }
        })
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd-MM-yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            ET_PEN_tgl_lahir.setText(sdf.format(myCalendar.time).toString())
        }
        ET_PEN_tgl_lahir.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(this@PendaftaranActivity, R.style.MySpinnerDatePickerStyle , dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
    }

    fun BT_ambil_antrian(no_telp: String) {
//        save_data()
        val jenis_kelamin:String
        jenis_kelamin = ConvertJenisKelamin(SP_PEN_jenis_kelamin.selectedItem.toString()).
                        getJenisKelamin(this)

        val dataPasien:DataPasien = DataPasien(
            ET_PEN_nama.text.toString(),
            ET_PEN_no_ktp.text.toString(),
            jenis_kelamin,
            ET_PEN_tgl_lahir.text.toString(),
            SP_PEN_status.selectedItem.toString(),
            SP_PEN_gol_darah.selectedItem.toString(),
            no_telp,
            ET_PEN_alamat.text.toString())
        apimain.services.addDataPasien(dataPasien).enqueue(object :
        Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    if (response.body()?.toString() == "SUKSES"){
                        val intent:Intent = Intent(applicationContext, AmbilAntrianActivity::class.java)
                        intent.putExtra("no_telp", no_telp)
                        startActivity(intent)
                    }else if (response.body()?.toString() == "GAGAL"){
                        Toast.makeText(applicationContext, "Proses pendaftaran gagal",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                StatusButton().enable(BT_PEN_ambil, PB_PEN_loading)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("response add:", t.message.toString())
                Toast.makeText(applicationContext, "Mohon maaf server sedang DOWN!!",
                    Toast.LENGTH_SHORT).show()
                StatusButton().enable(BT_PEN_ambil, PB_PEN_loading)
            }
        })
    }

//    fun save_data(){
//        val db = DataLokalDatabase(this)
//        GlobalScope.launch {
//            db.dataAntrianDao().insertAll(DataAntrianEntity(
//                0, intent.getStringExtra("no_telp"), "uncall", ET_PEN_no_ktp.text.toString().toInt() ))
//            val data = db.dataAntrianDao().getAll()
//            data?.forEach{
//                Log.d("DATABASE : ", it.toString())
//            }
//        }
//    }

    fun checkError(): Boolean{
        var status = true
        if (ET_PEN_nama.text!!.length <= 0){
            ET_PEN_nama.error = "nama kosong"
            status=false
        }
        if (ET_PEN_no_ktp.text!!.length <= 16){
            ET_PEN_no_ktp.error = "min 16 angka"
            status=false
        }
        if (ET_PEN_alamat.text!!.length == 0){
            ET_PEN_alamat.error = "alamat kosong"
            status = false
        }
        if (ET_PEN_tgl_lahir.text!!.length == 0){
            ET_PEN_tgl_lahir.error = "tgl lahir kosong"
            status=false
        }
        return status
    }

    fun updateLabel(myCalendar:Calendar) {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        ET_PEN_tgl_lahir.setText(sdf.format(myCalendar.time)?.toString())
    }
}

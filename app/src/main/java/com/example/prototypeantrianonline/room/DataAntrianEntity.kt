package com.example.prototypeantrianonline.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataantrianentity")
data class DataAntrianEntity(
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo(name = "no_telp") var no_telp:String,
    @ColumnInfo(name = "status") var status:String,
    @ColumnInfo(name = "no_antrian") var no_antrian:Int,
    @ColumnInfo(name = "jenis_pengobatan") var jenis_pengobatan:String
    )
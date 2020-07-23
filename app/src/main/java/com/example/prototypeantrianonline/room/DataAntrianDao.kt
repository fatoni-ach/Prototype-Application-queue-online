package com.example.prototypeantrianonline.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DataAntrianDao{
    @Query("select * from dataantrianentity")
    fun getAll(): List<DataAntrianEntity>

    @Query("select * from dataantrianentity where no_antrian like :no_antrian")
    fun findByNoAntrian(no_antrian: Int): DataAntrianEntity

    @Query("delete from dataantrianentity")
    fun deleteAll()

    @Query("SELECT count(*) FROM dataantrianentity")
    fun getCount(): Int

    @Insert
    fun insertAll(vararg antrianData: DataAntrianEntity)

    @Delete
    fun delete( antrian : DataAntrianEntity)

    @Update
    fun updateData( vararg antrianData:DataAntrianEntity)
}
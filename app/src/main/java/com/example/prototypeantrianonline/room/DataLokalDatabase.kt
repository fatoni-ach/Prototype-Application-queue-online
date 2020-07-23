package com.example.prototypeantrianonline.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DataAntrianEntity::class), version = 1)
abstract class DataLokalDatabase: RoomDatabase(){
    abstract fun dataAntrianDao(): DataAntrianDao

    companion object{
        @Volatile private var instance: DataLokalDatabase? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, DataLokalDatabase::class.java, "data.db").build()
    }
}
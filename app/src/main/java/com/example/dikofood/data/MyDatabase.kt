package com.example.dikofood.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase

@Database(version = 1, entities = [FoodData::class], exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract val foodDao: FoodDao
    companion object{
        private var database :MyDatabase? = null
        fun getDatabase(context: Context):MyDatabase{
            var instance = database
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext ,
                    MyDatabase::class.java ,
                    "DatabaseFood.db"
                    )
                    .allowMainThreadQueries()
                    .build()

            }
            return instance

        }


    }
}
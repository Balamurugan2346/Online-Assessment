package com.android.lokal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.lokal.data.DataClasses.JobListing


@Database(entities = [JobListing::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
 abstract class JobDatabase : RoomDatabase() {

   abstract fun jobDao() : JobDao



   companion object {
       @Volatile
       private var INSTANCE  : JobDatabase? = null

       fun getDatabase(context: Context): JobDatabase {
           return INSTANCE ?: synchronized(this) {
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   JobDatabase::class.java,
                   DatabaseName.name
               )
                   .build()
               INSTANCE = instance
               instance
           }
       }
   }
   }

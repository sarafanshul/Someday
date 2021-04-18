package com.example.cardveiwapp

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import android.util.Log
import androidx.room.TypeConverters

@Database(
    entities = [CardData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDataDao(): CardDataDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if( tempInstance != null )
                return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext ,
                    AppDatabase::class.java ,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
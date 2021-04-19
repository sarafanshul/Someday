package com.example.cardveiwapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.example.cardveiwapp.constant.DATABASE_NAME
import com.example.cardveiwapp.constant.PREPOPULATE_DATA
import com.example.cardveiwapp.utils.Converters
import java.util.concurrent.Executors

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
            val tempInstance =
                INSTANCE
            if( tempInstance != null )
                return tempInstance

            synchronized(this){
                val instance =
                    buildDatabase(
                        context
                    )
//                val instance = Room.databaseBuilder(
//                    context.applicationContext ,
//                    AppDatabase::class.java ,
//                    DATABASE_NAME
//                ).build()
                INSTANCE = instance
                return instance
            }
        }

        private fun buildDatabase( context: Context ) : AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext ,
                AppDatabase::class.java ,
                DATABASE_NAME
            ).addCallback(object :  RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase){
                    super.onCreate(db)
                    // pre-populate data
                    Executors.newSingleThreadExecutor().execute {
                        INSTANCE?.let {
                            it.cardDataDao().insertData(PREPOPULATE_DATA)
                        }
                    }
                }
            }

            ).build()
        }

    }
}
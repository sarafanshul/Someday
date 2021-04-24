package com.projectdelta.someday.Constant

import android.content.Context
import android.widget.Toast
import com.projectdelta.someday.Data.AppDatabase
import com.projectdelta.someday.Data.CardData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

const val DATABASE_NAME = "card-db"
var pendingIntentRequestCode : Int = 0 // increment pendingIntentRequestCode for new pending intent
val DAY_VALUE = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
const val NOTIFICATION_INTERVAL = 60L
const val CHANNEL_ID = "channelID"
const val CHANNEL_NAME = "channelNAME"

private var color_num = 0
private var _size = 7 // weekday only now
val PREPOPULATE_DATA = mutableListOf(
    CardData("Sunday", " ", mutableListOf() , ( color_num++ )%(_size)),
    CardData("Monday", " ", mutableListOf() , ( color_num++ )%(_size)),
    CardData("Tuesday", " ", mutableListOf() , ( color_num++ )%(_size)),
    CardData("Wednesday", " ", mutableListOf() , ( color_num++ )%(_size)),
    CardData("Thursday", " ", mutableListOf() , ( color_num++ )%(_size)),
    CardData("Friday", " ", mutableListOf() , ( color_num++ )%(_size)),
    CardData("Saturday", " ", mutableListOf() , ( color_num++ )%(_size))
)

fun nukeDatabase( _applicationContext : Context){
    GlobalScope.launch {
        val databaseDao = AppDatabase.getDatabase(_applicationContext).cardDataDao()
        PREPOPULATE_DATA.forEach {
            databaseDao.insertOrUpdate(it)
        }
    }
    Toast.makeText( _applicationContext , "All data has been cleared !" , Toast.LENGTH_SHORT ).show()
}
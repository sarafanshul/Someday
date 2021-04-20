package com.projectdelta.someday.constant

import com.projectdelta.someday.data.CardData
import java.util.*

const val DATABASE_NAME = "card-db"

val DAY_VALUE = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

val PREPOPULATE_DATA = mutableListOf(
    CardData("Sunday", " ", mutableListOf()),
    CardData("Monday", " ", mutableListOf()),
    CardData("Tuesday", " ", mutableListOf()),
    CardData("Wednesday", " ", mutableListOf()),
    CardData("Thursday", " ", mutableListOf()),
    CardData("Friday", " ", mutableListOf()),
    CardData("Saturday", " ", mutableListOf())
)

val weekDays = mutableListOf(
    "Sunday" ,
    "Monday" ,
    "Tuesday" ,
    "Wednesday" ,
    "Friday" ,
    "Saturday"
)
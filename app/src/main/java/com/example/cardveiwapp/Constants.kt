package com.example.cardveiwapp

const val DATABASE_NAME = "card-db"

val PREPOPULATE_DATA = mutableListOf(
    CardData( "Sunday" , "Rest Day !" , mutableListOf( "Groceries" , "Exercise" )  ) ,
    CardData( "Monday" , "Chest & Shoulders" , mutableListOf( "Learn" , "Exercise" )  ) ,
    CardData( "Tuesday" , "Legs & Triceps" , mutableListOf( "Date" , "Exercise" )  ) ,
    CardData( "Wednesday" , "Back & Biceps" , mutableListOf( "Project" , "Diet" ) ) ,
    CardData( "Thursday" , "Chest & Shoulders" , mutableListOf( "Exercise" , "Exercise" )  ) ,
    CardData( "Friday" , "Legs & Triceps" , mutableListOf( "Recharge" , "Exercise" )  ) ,
    CardData( "Saturday" , "Back & Biceps" , mutableListOf( "Bill" , "Exercise" ,"Project" , "Diet" ,"Learn" , "A very Very long string for testing Views"  )  )
)
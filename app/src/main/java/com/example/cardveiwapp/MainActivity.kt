package com.example.cardveiwapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		// Recycler View
		var _card_data = mutableListOf(
			CardData( "Sunday" , "Good Day !" , mutableListOf( "Groceries" , "Exercise" )  ) ,
			CardData( "Monday" , "Good Day !" , mutableListOf( "Learn" , "Exercise" )  ) ,
			CardData( "Tuesday" , "Good Day !" , mutableListOf( "Date" , "Exercise" )  ) ,
			CardData( "Wednesday" , "Good Day !" , mutableListOf( "Project" , "Diet" ) ) ,
			CardData( "Thursday" , "Good Day !" , mutableListOf( "Exercise" , "Exercise" )  ) ,
			CardData( "Friday" , "Good Day !" , mutableListOf( "Recharge" , "Exercise" )  ) ,
			CardData( "Saturday" , "Good Day !" , mutableListOf( "Bill" , "Exercise" )  )
		)
		val _adapter = RecyclerViewCardAdapter( _card_data )
		main_rv_main.adapter = _adapter
		main_rv_main.layoutManager = LinearLayoutManager( this )

	}
}
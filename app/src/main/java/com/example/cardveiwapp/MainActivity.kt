package com.example.cardveiwapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

		// onClick Listenr for Recycler View
		main_rv_main.addOnItemTouchListener( RecyclerItemClickListenr( this , main_rv_main , object : RecyclerItemClickListenr.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				Log.d( "Short Click" , "Clicked" ) // test
				Toast.makeText(this@MainActivity , "Short Click" , Toast.LENGTH_LONG).show()
			}
			override fun onItemLongClick(view: View?, position: Int) {
				Log.d( "Long Click" , "Clicked" )
				Toast.makeText(this@MainActivity , "Long Click" , Toast.LENGTH_LONG).show()
			}
		} ) )
	}
}
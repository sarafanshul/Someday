package com.example.cardveiwapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

	lateinit var adapter : RecyclerViewCardAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// Recycler View
		val _card_data = createDataDefault()
		adapter = RecyclerViewCardAdapter( _card_data )
		main_rv_main.adapter = adapter
		main_rv_main.layoutManager = LinearLayoutManager( this )


		// onClick Listenr for Recycler View
		main_rv_main.addOnItemTouchListener( RecyclerItemClickListenr( this , main_rv_main , object : RecyclerItemClickListenr.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				Log.d( "Short Click" , "Clicked" ) // test
				itemOnClickRecyclerView( this@MainActivity , view , position )
			}
			override fun onItemLongClick(view: View?, position: Int) {
				Log.d( "Long Click" , "Clicked" )

			}
		} ) )
	}

	private val SECOND_ACTIVITY_REQUEST_CODE = 0
	private var POSITION = 0
	private fun itemOnClickRecyclerView( context : Context , view: View, position: Int ) : Unit {
		val curData = adapter.cur_data[position]
		POSITION = position
		Intent( context , ActivityDetailedInfo::class.java ).also{
			it.putExtra( "DATA" , curData )
			startActivityForResult( it , SECOND_ACTIVITY_REQUEST_CODE )
		}
	}
	// This method is called when the second activity finishes
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		// Check that it is the SecondActivity with an OK result
		if(requestCode == SECOND_ACTIVITY_REQUEST_CODE ){
			if( resultCode == Activity.RESULT_OK ){
				val new_curData = data!!.getSerializableExtra("NEWDATA") as CardData
				Log.d( "New Data", new_curData.toString() )
				updateAdapter( new_curData )
			}
		}

	}
	fun updateAdapter(newData: CardData ) : Unit{
		adapter.cur_data[POSITION].tasks = newData.tasks
		adapter.notifyItemChanged( POSITION )
	}

	private fun createDataDefault( ) : MutableList<CardData>{
		var _card_data = mutableListOf(
			CardData( "Sunday" , "Good Day !" , mutableListOf( "Groceries" , "Exercise" )  ) ,
			CardData( "Monday" , "Good Day !" , mutableListOf( "Learn" , "Exercise" )  ) ,
			CardData( "Tuesday" , "Good Day !" , mutableListOf( "Date" , "Exercise" )  ) ,
			CardData( "Wednesday" , "Good Day !" , mutableListOf( "Project" , "Diet" ) ) ,
			CardData( "Thursday" , "Good Day !" , mutableListOf( "Exercise" , "Exercise" )  ) ,
			CardData( "Friday" , "Good Day !" , mutableListOf( "Recharge" , "Exercise" )  ) ,
			CardData( "Saturday" , "Good Day !" , mutableListOf( "Bill" , "Exercise" )  )
		)
		val dateInfo = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toInt()
		Log.d( "rotate val" , "${dateInfo}" )
		Log.d( "before rotate" ,_card_data.toString() )
		Collections.rotate( _card_data , -dateInfo + 1)
		Log.d( "after rotate" ,_card_data.toString() )
		return _card_data
	}

}
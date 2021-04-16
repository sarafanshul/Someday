package com.example.cardveiwapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// Recycler View
		val _card_data = createDataDefault()
		val _adapter = RecyclerViewCardAdapter( _card_data )
		main_rv_main.adapter = _adapter
		main_rv_main.layoutManager = LinearLayoutManager( this )

		fun clearFragmentStack( ) : Unit{
			val count = supportFragmentManager.backStackEntryCount
			for( i in 0 until count)
				supportFragmentManager.popBackStack()
		}
		clearFragmentStack()

		fun itemOnClickRecyclerView( view: View, position: Int ) : Unit {

			val selectedFragment = FragmentSelectedInfo()
			supportFragmentManager.beginTransaction().apply {
				replace( R.id.main_fl_1 , selectedFragment )
				addToBackStack(null)
				commit()
			}
		}

		// onClick Listenr for Recycler View
		main_rv_main.addOnItemTouchListener( RecyclerItemClickListenr( this , main_rv_main , object : RecyclerItemClickListenr.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				Log.d( "Short Click" , "Clicked" ) // test
				itemOnClickRecyclerView( view , position )
			}
			override fun onItemLongClick(view: View?, position: Int) {
				Log.d( "Long Click" , "Clicked" )

			}
		} ) )
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
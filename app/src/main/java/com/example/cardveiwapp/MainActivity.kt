package com.example.cardveiwapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

	lateinit var adapter : RecyclerViewCardAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Splash Screen
		setTheme( R.style.Theme_CardVeiwApp )

		setContentView(R.layout.activity_main)

		// Recycler View
		val _card_data = createDataDefault()
		adapter = RecyclerViewCardAdapter( _card_data )
		main_rv_main.adapter = adapter
		main_rv_main.layoutManager = LinearLayoutManager( this )


		// onClick Listenr for Recycler View
		main_rv_main.addOnItemTouchListener( RecyclerItemClickListenr( this , main_rv_main , object : RecyclerItemClickListenr.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				itemOnClickRecyclerView( this@MainActivity , view , position )
			} // to Activity 2
			override fun onItemLongClick(view: View?, position: Int) {
				val _dialogLayout = layoutInflater.inflate( R.layout.activity_detailed_info_alert_dialog , null)
				var alert_et = _dialogLayout.findViewById<EditText>( R.id.activity_detailed_info_alert_dialog_et_main )
				val _displayTextAlertDialog = MaterialAlertDialogBuilder( this@MainActivity )
					.setTitle("New Sub Task for ${_card_data[position].title} ?")
					.setView( _dialogLayout )
					.setPositiveButton("OK"){ _ ,_ ->
						val inp = alert_et.text.toString()
						if( inp?.length > 0 ) {
							_card_data[position].subtitle = inp
							adapter.notifyItemChanged(position)
							Snackbar.make(main_rv_main ,"Sub Task for ${_card_data[position].title} Changed !" , Snackbar.LENGTH_SHORT).show()
						}
					}.setNegativeButton("CANCEL"){ _ ,_ ->
					}.create()
				_displayTextAlertDialog.window?.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE )
				_displayTextAlertDialog.show()
			} // Changes Subtext
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
		adapter.cur_data[POSITION].tasks = newData.tasks // Check HERE if Directly "curData" or from adapter
		adapter.notifyItemChanged( POSITION )
	}

	private fun createDataDefault( ) : MutableList<CardData>{
		var _card_data = mutableListOf(
			CardData( "Sunday" , "Rest Day !" , mutableListOf( "Groceries" , "Exercise" )  ) ,
			CardData( "Monday" , "Chest & Shoulders" , mutableListOf( "Learn" , "Exercise" )  ) ,
			CardData( "Tuesday" , "Legs & Triceps" , mutableListOf( "Date" , "Exercise" )  ) ,
			CardData( "Wednesday" , "Back & Biceps" , mutableListOf( "Project" , "Diet" ) ) ,
			CardData( "Thursday" , "Chest & Shoulders" , mutableListOf( "Exercise" , "Exercise" )  ) ,
			CardData( "Friday" , "Legs & Triceps" , mutableListOf( "Recharge" , "Exercise" )  ) ,
			CardData( "Saturday" , "Back & Biceps" , mutableListOf( "Bill" , "Exercise" ,"Project" , "Diet" ,"Learn" , "A very Very long string for testing Views"  )  )
		)
		val dateInfo = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toInt()
		Log.d( "rotate val" , "${dateInfo}" )
		Log.d( "before rotate" ,_card_data.toString() )
		Collections.rotate( _card_data , -dateInfo + 1)
		Log.d( "after rotate" ,_card_data.toString() )
		return _card_data
	}

}
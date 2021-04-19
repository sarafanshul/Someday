package com.projectdelta.someday

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectdelta.someday.activity.ActivityDetailedInfo
import com.projectdelta.someday.adapters.RecyclerViewCardAdapter
import com.projectdelta.someday.data.CardData
import com.projectdelta.someday.utils.RecyclerItemClickListenr
import com.projectdelta.someday.viewModels.CardViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	lateinit var adapter : RecyclerViewCardAdapter
	private lateinit var cardViewModel: CardViewModel
	private var POSITION = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Splash Screen
		setTheme( R.style.Theme_CardVeiwApp )

		// https://github.com/googlecodelabs/android-room-with-a-view/issues/155
		cardViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( CardViewModel::class.java )

		setContentView(R.layout.activity_main)

		// Recycler View Adapter
		adapter = RecyclerViewCardAdapter()
		main_rv_main.adapter = adapter
		main_rv_main.layoutManager = LinearLayoutManager( this )

		// sets up live data for Recycler View
		cardViewModel.getAllData.observe( this , androidx.lifecycle.Observer {cardData ->
			adapter.setData( cardData )
			}
		)

		// onClick Listener for Recycler View
		main_rv_main.addOnItemTouchListener(
			RecyclerItemClickListenr(
				this,
				main_rv_main,
				object :
					RecyclerItemClickListenr.OnItemClickListener {
					override fun onItemClick(view: View, position: Int) {
						itemOnClickRecyclerView(this@MainActivity, position)
					} // to Activity 2

					override fun onItemLongClick(view: View?, position: Int) {

						val _dialogLayout = layoutInflater.inflate(
							R.layout.activity_detailed_info_alert_dialog,
							null
						)
						var alert_et =
							_dialogLayout.findViewById<EditText>(R.id.activity_detailed_info_alert_dialog_et_main)

						val _displayTextAlertDialog = MaterialAlertDialogBuilder(this@MainActivity)
							.setTitle("New Sub Task for ${adapter.cur_data[position].title} ?")
							.setView(_dialogLayout)
							.setPositiveButton("OK") { _, _ ->
								val inp = alert_et.text.toString()
								if (inp.isNotEmpty()) {
									adapter.cur_data[position].subtitle = inp
									POSITION = position
									updateAdapter(adapter.cur_data[position])
									Snackbar.make(
										main_rv_main,
										"Sub Task for ${adapter.cur_data[position].title} Changed !",
										Snackbar.LENGTH_SHORT
									).show()
								}
							}.setNegativeButton("CANCEL") { _, _ ->
							}.create()
						_displayTextAlertDialog.show()
					} // Changes Subtext

				}
			)
		)
	}

	// Starts 2nd Activity from RecyclerView(short press)
	private val SECOND_ACTIVITY_REQUEST_CODE = 0
	private fun itemOnClickRecyclerView( context : Context , position: Int ) : Unit {
		val curData = adapter.cur_data[position]
		POSITION = position
		Intent( context , ActivityDetailedInfo::class.java ).also{
			it.putExtra( "DATA" , curData )
			startActivity( it )
//			startActivityForResult( it , SECOND_ACTIVITY_REQUEST_CODE )
		}
	}

	// This method is called when the second activity finishes (depriciated , moved to DAO )
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		// Check that it is the SecondActivity with an OK result
		if(requestCode == SECOND_ACTIVITY_REQUEST_CODE ){
			if( resultCode == Activity.RESULT_OK ){
				val new_curData = data!!.getSerializableExtra("NEWDATA") as CardData
//				Log.d( "New Data", new_curData.toString() )
//				updateAdapter( new_curData )
			}
		}

	}

	// updates the data
	fun updateAdapter(newData: CardData) : Unit{
		adapter.cur_data[POSITION].tasks = newData.tasks // Check HERE if Directly "curData" or from adapter
		adapter.cur_data[POSITION].subtitle = newData.subtitle
		adapter.notifyItemChanged( POSITION )
		cardViewModel.updateCard( adapter.cur_data[POSITION] )
	}

	// implement onDestory function for persistence
	override fun onDestroy() {
		super.onDestroy()
//		cardViewModel.getAllData.value = adapter.cur_data
	}
}
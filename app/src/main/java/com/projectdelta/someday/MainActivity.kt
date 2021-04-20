package com.projectdelta.someday

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectdelta.someday.activity.ActivityDetailedInfo
import com.projectdelta.someday.adapters.RecyclerViewCardAdapter
import com.projectdelta.someday.data.CardData
import com.projectdelta.someday.utils.RecyclerItemClickListenr
import com.projectdelta.someday.viewModels.CardViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.projectdelta.someday.activity.SettingsActivity
import com.projectdelta.someday.fragment.SettingsFragment
import com.projectdelta.someday.utils.CreateNotification
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	lateinit var adapter : RecyclerViewCardAdapter
	private lateinit var cardViewModel: CardViewModel
	lateinit var toggle : ActionBarDrawerToggle
	private var POSITION = 0
	private val CHANNEL_ID = "channelID"
	private val CHANNEL_NAME = "channelNAME"
	private val NOTIFICATION_ID = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Splash Screen
		setTheme( R.style.Theme_CardVeiwApp )

		// https://github.com/googlecodelabs/android-room-with-a-view/issues/155
		cardViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( CardViewModel::class.java )

		setContentView(R.layout.activity_main)

		// create notification channel
		val notificationAdapter =  CreateNotification()
		notificationAdapter.createNotificationChannel(this)

		// create notification
		val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
		cardViewModel.today.observe(this ,androidx.lifecycle.Observer { cardData -> notificationAdapter.setToday( cardData ) })
		if( sharedPreferences.getBoolean("notifications" , false ) )
//			notificationAdapter.newNotification(this , NOTIFICATION_ID )

		// Slider Menu
		toggle = ActionBarDrawerToggle(this , main_drawer_main , R.string.open , R.string.close )
		main_drawer_main.addDrawerListener( toggle )
		toggle.syncState()

		supportActionBar?.setDisplayHomeAsUpEnabled( true )

		// nav menu on item click listner
		main_nav_view.setNavigationItemSelectedListener {
			when( it.itemId ){
				R.id.main_menu_settings -> goSettings( this )
				R.id.main_menu_credits -> goSettings( this )
				R.id.main_menu_feedback -> goSettings( this )
			}
			true
		}

		// Recycler View Adapter
		adapter = RecyclerViewCardAdapter()
		main_rv_main.adapter = adapter
		main_rv_main.layoutManager = LinearLayoutManager( this )

		// sets up live data for Recycler View
		cardViewModel.getAllByOrder.observe( this , androidx.lifecycle.Observer {cardData ->
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
	private val SETTING_REQUEST_CODE = 100
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
		if(requestCode == SETTING_REQUEST_CODE ){
			if( resultCode == Activity.RESULT_OK ){

			}
		}

	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if ( toggle.onOptionsItemSelected(item) )
			return true
		return super.onOptionsItemSelected(item)
	}

	// updates the data
	fun updateAdapter(newData: CardData) : Unit{
		adapter.cur_data[POSITION].tasks = newData.tasks // Check HERE if Directly "curData" or from adapter
		adapter.cur_data[POSITION].subtitle = newData.subtitle
		adapter.notifyItemChanged( POSITION )
		cardViewModel.updateCard( adapter.cur_data[POSITION] )
	}

	fun goSettings( context: Context ){
		Intent( context , SettingsActivity::class.java ).also{
			startActivityForResult( it , SETTING_REQUEST_CODE)
		}
	}

	fun goCredits( view: View ){

	}

	fun goFeedback( view: View ){

	}

	// implement onDestory function for persistence
	override fun onDestroy() {
		super.onDestroy()
//		cardViewModel.getAllData.value = adapter.cur_data
	}
}
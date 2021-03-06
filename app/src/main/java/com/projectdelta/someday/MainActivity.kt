package com.projectdelta.someday

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.google.android.material.card.MaterialCardView
import com.projectdelta.someday.Activity.ActivityDetailedInfo
import com.projectdelta.someday.Adapters.RecyclerViewCardAdapter
import com.projectdelta.someday.Data.CardData
import com.projectdelta.someday.Util.RecyclerItemClickListenr
import com.projectdelta.someday.ViewModels.CardViewModel
import com.projectdelta.someday.Activity.SettingsActivity
import com.projectdelta.someday.Util.NotificationUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	lateinit var adapter : RecyclerViewCardAdapter
	private lateinit var cardViewModel: CardViewModel
	lateinit var toggle : ActionBarDrawerToggle
	private var POSITION = 0
	private val CHANNEL_ID = "channelID"
	private val CHANNEL_NAME = "channelNAME"
	private val NOTIFICATION_ID = 0
	lateinit var today : CardData
	lateinit var notificationWorkRequest : PeriodicWorkRequest


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Splash Screen
		setTheme( R.style.Theme_CardVeiwApp )

		// https://github.com/googlecodelabs/android-room-with-a-view/issues/155
		cardViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( CardViewModel::class.java )

		setContentView(R.layout.activity_main)


		NotificationUtil.context = applicationContext // Singleton Implementation
		NotificationUtil.createNotificationChannel()


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
						itemLongClickRecyclerView(view , position)
					} // Changes Subtext

				}
			)
		)

	}

	private fun itemLongClickRecyclerView(view: View? , position: Int) {
		if( adapter.cur_data[position].tasks.size < 2 ) return
		adapter.isSelected[position] = !adapter.isSelected[position]
		val cv = view?.findViewById<MaterialCardView>( R.id.recycler_cardview_cv_1 )
		val tw = view?.findViewById<TextView>( R.id.recycler_cardview_tv_content )
		if( adapter.isSelected[position] ){ // selected
			val params = cv?.layoutParams
			params?.width = cv?.layoutParams?.width
			params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
			cv?.layoutParams = params

			var str : String  = ""
			for( i in 0 until minOf(6 , adapter.cur_data[ position ].tasks.size ) ) str = str + adapter.cur_data[ position ].tasks[i] + "\n"
			tw?.isSingleLine = false
			tw?.text = if(adapter.cur_data[ position ].tasks.size > 0) str else "Every Thing Done !!"
			cv?.animate()?.setInterpolator(AccelerateInterpolator())?.setDuration(3000)
		}
		else {
			val params = cv?.layoutParams
			params?.width = cv?.layoutParams?.width
			params?.height = resources.getDimension(R.dimen.card_height_base).toInt()
			cv?.layoutParams = params
			tw?.isSingleLine = true
			tw?.text = if(adapter.cur_data[ position ].tasks.size > 0)  adapter.cur_data[ position ].tasks[ 0 ] else "Every Thing Done !!"
			cv?.animate()?.setInterpolator(AccelerateInterpolator())?.setDuration(3000)
		}
	}

	private fun itemOnClickRecyclerView( context : Context , position: Int ) : Unit {
		val curData = adapter.cur_data[position]
		POSITION = position
		Intent( context , ActivityDetailedInfo::class.java ).also{
			it.putExtra( "DATA" , curData )
			startActivity( it )
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if ( toggle.onOptionsItemSelected(item) )
			return true
		return super.onOptionsItemSelected(item)
	}

	// updates the data (depreciated)
	fun updateAdapter(newData: CardData) : Unit{
		adapter.cur_data[POSITION].tasks = newData.tasks // Check HERE if Directly "curData" or from adapter
		adapter.cur_data[POSITION].subtitle = newData.subtitle
		adapter.notifyItemChanged( POSITION )
		cardViewModel.updateCard( adapter.cur_data[POSITION] )
	}

	fun goSettings( context: Context ){
		Intent( context , SettingsActivity::class.java ).also{
			startActivity( it )
		}
	}
}
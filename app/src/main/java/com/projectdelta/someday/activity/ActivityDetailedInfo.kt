package com.projectdelta.someday.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectdelta.someday.R
import com.projectdelta.someday.adapters.RecyclerViewTasksAdapter
import com.projectdelta.someday.data.CardData
import com.projectdelta.someday.viewModels.TaskViewModel
import com.projectdelta.someday.utils.SwipeToDelete
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detailed_info.*

// https://tutorial.eyehunts.com/android/getting-a-result-from-an-activity-android-startactivityforresult-example-kotlin/

class ActivityDetailedInfo : AppCompatActivity() {

	lateinit var adapter: RecyclerViewTasksAdapter
	private lateinit var taskViewModel : TaskViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Splash Screen
		setTheme(R.style.Theme_CardVeiwApp)

		taskViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( TaskViewModel::class.java )

		setContentView(R.layout.activity_detailed_info)

		// Data form Main Activity
		val curData = intent.getSerializableExtra("DATA") as CardData

		// for database Querying
		val title = curData.title
		activity_detailed_info_tw_heading.text = curData.title


		// Activity - 2 recycler view adapter
		adapter = RecyclerViewTasksAdapter()
		activity_detailed_info_rv_main.adapter = adapter
		activity_detailed_info_rv_main.layoutManager = LinearLayoutManager(this)

		taskViewModel.getDataById( title ).observe( this , androidx.lifecycle.Observer {cardData ->
			adapter.setData( cardData )
		} )


		// Helper util for Swipe to Delete
		var itemTouchHelper = ItemTouchHelper(
			SwipeToDelete(
				adapter
			)
		)
		itemTouchHelper.attachToRecyclerView( activity_detailed_info_rv_main )

		// alert dialog for FAB
		activity_detailed_info_efab_add.setOnClickListener {
			val _dialogLayout = layoutInflater.inflate(R.layout.activity_detailed_info_alert_dialog, null)
			var alert_et = _dialogLayout.findViewById<EditText>(R.id.activity_detailed_info_alert_dialog_et_main)
			alert_et.hint = "Here !"
			val _displayTextAlertDialog = MaterialAlertDialogBuilder( this )
				.setTitle("New Task ?")
				.setView( _dialogLayout )
				.setPositiveButton("OK"){ _ ,_ ->
					val inp = alert_et.text.toString()
					if( inp?.length > 0 )
						updateTask( inp )
				}.setNegativeButton("CANCEL"){ _ ,_ ->
				}.create()

			_displayTextAlertDialog.window?.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE )
			_displayTextAlertDialog.show()
		}
	}

	// Updates Task
	fun updateTask( newTask : String ) : Unit {
		Snackbar.make( activity_detailed_info_rv_main , "${newTask} Added !" , Snackbar.LENGTH_LONG).apply{
			anchorView = activity_detailed_info_efab_add
		}.show() // for SnackBar Above the Create Button
		adapter.cardData.tasks.add( newTask )
		taskViewModel.updateCard( adapter.cardData )
		adapter.notifyItemChanged( adapter.itemCount )
	}

	override fun onPause() {
		super.onPause()
		taskViewModel.updateCard( adapter.cardData )
	}

	override fun onStop() {
		super.onStop()
		taskViewModel.updateCard( adapter.cardData )

	}

	override fun onDestroy() {
		super.onDestroy()
		taskViewModel.updateCard( adapter.cardData )
	}

}
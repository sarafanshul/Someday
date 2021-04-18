package com.example.cardveiwapp

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detailed_info.*

// https://tutorial.eyehunts.com/android/getting-a-result-from-an-activity-android-startactivityforresult-example-kotlin/

class ActivityDetailedInfo : AppCompatActivity() {

	lateinit var adapter: RecyclerViewTasksAdapter
	private lateinit var cardViewModel : CardViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Splash Screen
		setTheme( R.style.Theme_CardVeiwApp )

		cardViewModel = ViewModelProvider( this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.application) ).get( CardViewModel::class.java )

		setContentView(R.layout.activity_detailed_info)

		// Data form Main Activity
		val curData = intent.getSerializableExtra("DATA") as CardData

		// Copy of Data
		var tasksData = curData.tasks
		activity_detailed_info_tw_heading.text = curData.title

		// Activite-2 recycler view adapter
		adapter = RecyclerViewTasksAdapter( tasksData )
		activity_detailed_info_rv_main.adapter = adapter
		activity_detailed_info_rv_main.layoutManager = LinearLayoutManager(this)

		fun updateTask( newTask : String ) : Unit {
			Snackbar.make( activity_detailed_info_rv_main , "${newTask} Added !" , Snackbar.LENGTH_LONG).apply{
				anchorView = activity_detailed_info_efab_add
			}.show() // for SnackBar Above the Create Button
			tasksData.add( newTask )
			adapter.notifyItemChanged( adapter.itemCount )
		}

		var itemTouchHelper = ItemTouchHelper( SwipeToDelete(adapter) )
		itemTouchHelper.attachToRecyclerView( activity_detailed_info_rv_main )

		// alert dialog for fab
		activity_detailed_info_efab_add.setOnClickListener {
			val _dialogLayout = layoutInflater.inflate( R.layout.activity_detailed_info_alert_dialog , null)
			var alert_et = _dialogLayout.findViewById<EditText>( R.id.activity_detailed_info_alert_dialog_et_main )
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

		curData.tasks = tasksData

		// return of startActivityForResult
		val returnIntent = Intent()
		returnIntent.putExtra( "NEWDATA" , curData )
		setResult( Activity.RESULT_OK , returnIntent )
	}

}
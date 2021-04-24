package com.projectdelta.someday.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectdelta.someday.R
import com.projectdelta.someday.Adapters.RecyclerViewTasksAdapter
import com.projectdelta.someday.Data.CardData
import com.projectdelta.someday.ViewModels.TaskViewModel
import com.projectdelta.someday.Util.SwipeToDelete
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detailed_info.*

// https://tutorial.eyehunts.com/android/getting-a-result-from-an-activity-android-startactivityforresult-example-kotlin/

class ActivityDetailedInfo : AppCompatActivity() {

	lateinit var adapter: RecyclerViewTasksAdapter
	private lateinit var taskViewModel : TaskViewModel

	// https://www.youtube.com/watch?v=umCX1-Tq25k (EXPANDABLE FAB)
	private val rotateOpen : Animation by lazy{ AnimationUtils.loadAnimation(this , R.anim.rotate_open_anim) }
	private val rotateClose : Animation by lazy{ AnimationUtils.loadAnimation(this , R.anim.rotate_close_anim) }
	private val fromBottom : Animation by lazy{ AnimationUtils.loadAnimation(this , R.anim.from_bottom_anim) }
	private val toBottom : Animation by lazy{ AnimationUtils.loadAnimation(this , R.anim.to_bottom_anim) }
	private  var fabClicked = false

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
			activity_detailed_info_tv_subtask.text = cardData.subtitle
		} )


		// Helper util for Swipe to Delete
		var itemTouchHelper = ItemTouchHelper(
			SwipeToDelete(
				adapter
			)
		)
		itemTouchHelper.attachToRecyclerView( activity_detailed_info_rv_main )

		// alert dialog for FAB
		activity_detailed_info_fab_extract.setOnClickListener {
			onExtractClicked()
		}

		activity_detailed_info_fab_add.setOnClickListener {
			addTask()
		}

		activity_detailed_info_fab_add_sub.setOnClickListener{
			addSubtask()
		}

	}

	fun addTask(){
		val _dialogLayout = layoutInflater.inflate(R.layout.activity_detailed_info_alert_dialog, null)
		var alert_et = _dialogLayout.findViewById<EditText>(R.id.activity_detailed_info_alert_dialog_et_main)
		alert_et.hint = "Here !"
		val _displayTextAlertDialog = MaterialAlertDialogBuilder( this@ActivityDetailedInfo )
				.setTitle("New Task for ${adapter.cardData.title} ?")
				.setView( _dialogLayout )
				.setPositiveButton("OK"){ _ ,_ ->
					val inp = alert_et.text.toString()
					if( inp?.length > 0 )
						updateTask( inp )
				}.setNegativeButton("CANCEL"){ _ ,_ ->
				}.create()

		_displayTextAlertDialog.window?.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE )
		_displayTextAlertDialog.show()
		onExtractClicked()
	}

	fun addSubtask(){
		val _dialogLayout = layoutInflater.inflate(R.layout.activity_detailed_info_alert_dialog, null)
		var alert_et = _dialogLayout.findViewById<EditText>(R.id.activity_detailed_info_alert_dialog_et_main)
		alert_et.hint = "Here !"
		val _displayTextAlertDialog = MaterialAlertDialogBuilder( this@ActivityDetailedInfo )
				.setTitle("${adapter.cardData.title}'s new subtask ?")
				.setView( _dialogLayout )
				.setPositiveButton("OK"){ _ ,_ ->
					val inp = alert_et.text.toString()
					if( inp?.length > 0 )
						updateSubtask( inp )
				}.setNegativeButton("CANCEL"){ _ ,_ ->
				}.create()

		_displayTextAlertDialog.window?.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE )
		_displayTextAlertDialog.show()
		onExtractClicked()
	}

	fun updateSubtask( newSubtsk : String ){
		Snackbar.make( activity_detailed_info_rv_main , "Subtask for ${adapter.cardData.title} changed !" , Snackbar.LENGTH_LONG).apply{
			anchorView = activity_detailed_info_fab_extract
		}.show() // for SnackBar Above the Create Button
		adapter.cardData.subtitle = newSubtsk
		taskViewModel.updateCard( adapter.cardData )
		adapter.notifyDataSetChanged()
	}

	// Updates Task
	fun updateTask( newTask : String ) : Unit {
		Snackbar.make( activity_detailed_info_rv_main , "${newTask} Added !" , Snackbar.LENGTH_LONG).apply{
			anchorView = activity_detailed_info_fab_extract
		}.show() // for SnackBar Above the Create Button
		adapter.cardData.tasks.add( newTask )
		taskViewModel.updateCard( adapter.cardData )
		adapter.notifyItemChanged( adapter.itemCount )
	}

	fun onExtractClicked(){
		fabSetVisiblity()
		fabSetAnimation()
		fabSetClickable()
		fabClicked = !fabClicked
	}

	fun fabSetAnimation(  ){
		if(!fabClicked){
			activity_detailed_info_fab_add.visibility = View.VISIBLE
			activity_detailed_info_fab_add_sub.visibility = View.VISIBLE
		}else {
			activity_detailed_info_fab_add.visibility = View.GONE
			activity_detailed_info_fab_add_sub.visibility = View.GONE // GONE ?
		}
	}

	fun fabSetVisiblity(){
		if(!fabClicked){
			activity_detailed_info_fab_add.startAnimation(fromBottom)
			activity_detailed_info_fab_add_sub.startAnimation(fromBottom)
			activity_detailed_info_fab_extract.startAnimation(rotateOpen)
		}else {
			activity_detailed_info_fab_add.startAnimation(toBottom)
			activity_detailed_info_fab_add_sub.startAnimation(toBottom)
			activity_detailed_info_fab_extract.startAnimation(rotateClose)
		}
	}

	fun fabSetClickable(){
		if( !fabClicked ){
			activity_detailed_info_fab_add.isContextClickable = true
			activity_detailed_info_fab_add_sub.isContextClickable = true
			activity_detailed_info_fab_add.isClickable = true
			activity_detailed_info_fab_add_sub.isClickable = true
		}else {
			activity_detailed_info_fab_add.isContextClickable = false
			activity_detailed_info_fab_add_sub.isContextClickable = false
			activity_detailed_info_fab_add.isClickable = false
			activity_detailed_info_fab_add_sub.isClickable = false
		}
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
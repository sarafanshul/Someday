package com.example.cardveiwapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detailed_info.*

// https://tutorial.eyehunts.com/android/getting-a-result-from-an-activity-android-startactivityforresult-example-kotlin/

class ActivityDetailedInfo : AppCompatActivity() {

	lateinit var adapter: RecyclerViewTasksAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detailed_info)

		val curData = intent.getSerializableExtra("DATA") as CardData

		var tasksData = curData.tasks
		activity_detailed_info_tw_heading.text = curData.title

		adapter = RecyclerViewTasksAdapter( tasksData )
		activity_detailed_info_rv_main.adapter = adapter
		activity_detailed_info_rv_main.layoutManager = LinearLayoutManager(this)

		fun updateTask( newTask : String ) : Unit {
			tasksData.add( newTask )
			adapter.notifyDataSetChanged()
		}

		activity_detailed_info_btn_add.setOnClickListener {
			val _dialogLayout = layoutInflater.inflate( R.layout.activity_detailed_info_alert_dialog , null)
			var alert_et = _dialogLayout.findViewById<EditText>( R.id.activity_detailed_info_alert_dialog_et_main )
			val _displayTextAlertDialog = AlertDialog.Builder( this )
				.setTitle("Add a New Task")
				.setMessage("Enter Task bellow")
				.setView( _dialogLayout )
				.setPositiveButton("OK"){ _ ,_ ->
					val inp = alert_et.text.toString()
					updateTask( inp )
				}.setNegativeButton("CANCEL"){ _ ,_ ->}
				.create()

			_displayTextAlertDialog.show()
		}

		curData.tasks = tasksData

		// return of startActivityForResult
		val returnIntent = Intent()
		returnIntent.putExtra( "NEWDATA" , curData )
		setResult( Activity.RESULT_OK , returnIntent )
	}

}
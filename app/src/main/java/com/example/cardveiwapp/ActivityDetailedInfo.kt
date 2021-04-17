package com.example.cardveiwapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detailed_info.*

// https://tutorial.eyehunts.com/android/getting-a-result-from-an-activity-android-startactivityforresult-example-kotlin/

class ActivityDetailedInfo : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detailed_info)

		val curData = intent.getSerializableExtra("DATA") as CardData
		Log.d( "CurData" , curData.tasks.joinToString("\n") )
		activity_detailed_info_tw_content.text = curData.tasks.joinToString("\n")
		curData.tasks.add( "Add Recycler View" )
		val returnIntent = Intent()
		returnIntent.putExtra( "NEWDATA" , curData )
		setResult( Activity.RESULT_OK , returnIntent )
	}
}
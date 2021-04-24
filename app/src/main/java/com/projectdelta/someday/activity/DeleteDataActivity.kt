package com.projectdelta.someday.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.projectdelta.someday.R
import com.projectdelta.someday.Constant.nukeDatabase
import kotlinx.android.synthetic.main.activity_delete_data.*

class DeleteDataActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setTheme( R.style.Theme_CardVeiwApp )
		setContentView(R.layout.activity_delete_data)

		activity_delete_btn_delete.setOnClickListener {
			val displayAlertDialog = MaterialAlertDialogBuilder(this).apply{
				setTitle("Delete all my data")
				setMessage("Data , once deleted cannot be retrieved !")
				setPositiveButton("Delete Anyway !"){_ , _ ->
					nukeDatabase( applicationContext )
				}
				setNegativeButton("CANCEL"){_ , _ -> }
			}.create()
			displayAlertDialog.show()
		}
	}

}
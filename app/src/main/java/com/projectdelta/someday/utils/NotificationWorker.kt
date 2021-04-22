package com.projectdelta.someday.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.projectdelta.someday.MainActivity
import com.projectdelta.someday.R
import com.projectdelta.someday.constant.CHANNEL_ID
import com.projectdelta.someday.constant.CHANNEL_NAME
import com.projectdelta.someday.data.CardData

class NotificationWorker(appContext: Context, workerParams: WorkerParameters):
		Worker(appContext, workerParams) {

	val intent = Intent( appContext , MainActivity::class.java).apply {
		flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
	}
	val pendingIntent: PendingIntent = PendingIntent.getActivity(appContext, 0, intent, 0)
	val builder = NotificationCompat.Builder( appContext , CHANNEL_ID ).apply{
		setSmallIcon(R.drawable.ic_todo)
		setContentTitle("Today's tasks are pending !")
		setContentText("Tap to view more.")
//            setOngoing( true )
		setContentIntent(pendingIntent)
		setAutoCancel(true)
		setPriority( NotificationCompat.PRIORITY_HIGH )
	}

	override fun doWork(): Result {

		createNotificationChannel()
		Toast.makeText(applicationContext , "in work manager", Toast.LENGTH_LONG).show()
		with(NotificationManagerCompat.from(applicationContext)) {
			notify(1, builder.build() )
		}
		val today : String = inputData.getString("TODAY") ?: return Result.failure()
		// Do the work here--in this case, upload the images.
//		uploadImages()

		// Indicate whether the work finished successfully with the Result
		return Result.success()
	}

	fun createNotificationChannel(){
		val channel = NotificationChannel( CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_LOW ).apply {
			enableLights(true)
		}
		val manager = applicationContext.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
		manager.createNotificationChannel( channel )
	}

}
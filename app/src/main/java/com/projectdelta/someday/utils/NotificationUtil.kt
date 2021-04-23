package com.projectdelta.someday.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.projectdelta.someday.MainActivity
import com.projectdelta.someday.R
import com.projectdelta.someday.constant.CHANNEL_ID
import com.projectdelta.someday.constant.CHANNEL_NAME
import com.projectdelta.someday.data.CardData

object NotificationUtil {

	lateinit var context: Context
	var data:CardData = CardData("Today" , "Tap to view More" , mutableListOf())

	fun createNotificationChannel( ){
		Log.d("NotificationUtil|WorkerWrapper|Main" , "createNotification")
		val channel = NotificationChannel( CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_LOW ).apply {
			enableLights(true)
		}
		val manager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
		manager.createNotificationChannel( channel )
	}

	fun newNotification( context_worker: Context , NOTIFICATION_ID : Int = 1 ){
		Log.d("NotificationUtil|WorkerWrapper" , "newNotification")
		val intent = Intent(context_worker, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}
		val pendingIntent: PendingIntent = PendingIntent.getActivity(context_worker, 0, intent, 0)
		val builder = NotificationCompat.Builder( context_worker , CHANNEL_ID ).apply{
			setSmallIcon(R.drawable.ic_todo)
			setContentTitle(data.title)
			setContentText(data.subtitle)
//            setOngoing( true )
			setContentIntent(pendingIntent)
			setAutoCancel(true)
			setPriority( NotificationCompat.PRIORITY_HIGH )
		}

		with(NotificationManagerCompat.from(context_worker)) {
			// notificationId is a unique int for each notification that you must define
			notify( NOTIFICATION_ID , builder.build())
		}
	}
}
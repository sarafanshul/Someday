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

object NotificationUtil {

	lateinit var context: Context

	fun createNotificationChannel( ){
		Log.d("NotificationUtil|WorkerWrapper|Main" , "createNotification")
		val channel = NotificationChannel( CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_LOW ).apply {
			enableLights(true)
		}
		val manager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
		manager.createNotificationChannel( channel )
	}

	fun newNotification( NOTIFICATION_ID : Int = 1 ){
		Log.d("NotificationUtil|WorkerWrapper" , "newNotification")
		val intent = Intent(context, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}
		val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
		val builder = NotificationCompat.Builder( context , CHANNEL_ID ).apply{
			setSmallIcon(R.drawable.ic_todo)
			setContentTitle("Welcome Back")
			setContentText("Tap to view more.")
//            setOngoing( true )
			setContentIntent(pendingIntent)
			setAutoCancel(true)
			setPriority( NotificationCompat.PRIORITY_HIGH )
		}

		with(NotificationManagerCompat.from(context)) {
			// notificationId is a unique int for each notification that you must define
			notify( NOTIFICATION_ID , builder.build())
		}
	}
}
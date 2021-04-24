package com.projectdelta.someday.Util

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
import com.projectdelta.someday.Constant.CHANNEL_ID
import com.projectdelta.someday.Constant.CHANNEL_NAME
import com.projectdelta.someday.Constant.pendingIntentRequestCode
import com.projectdelta.someday.Data.CardData



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
		if( data.tasks.size == 0 ) return  // dont fire for empty tasks
		val intent = Intent(context_worker, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}
		var inboxData = NotificationCompat.InboxStyle()
		data.tasks.forEach { if(it != null ) inboxData.addLine(it) }
		val _title = " Tasks scheduled for ${data.title}"
		val pendingIntent: PendingIntent = PendingIntent.getActivity(context_worker, pendingIntentRequestCode, intent, 0) // increment pendingIntentRequestCode for new pending intent
		val builder = NotificationCompat.Builder( context_worker , CHANNEL_ID ).apply{
			setSmallIcon(R.drawable.ic_todo)
			setContentTitle(_title)
			setContentText(data.subtitle)
			setStyle( inboxData )
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
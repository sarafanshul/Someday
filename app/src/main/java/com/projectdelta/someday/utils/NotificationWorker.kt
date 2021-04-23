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
import com.projectdelta.someday.constant.DAY_VALUE
import com.projectdelta.someday.data.AppDatabase
import com.projectdelta.someday.data.CardData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class NotificationWorker(appContext: Context, workerParams: WorkerParameters):
		Worker(appContext, workerParams) {

	override fun doWork(): Result {

		Log.d( "WorkerWrapper" , "DoWork" )

		var notification_data = CardData("Hello" , "Tap to view today's tasks" , mutableListOf())

		val job = GlobalScope.launch {
			NotificationUtil.data = AppDatabase.getDatabase(applicationContext).cardDataDao().getToday(
				DAY_VALUE)
			NotificationUtil.newNotification( applicationContext )
		}
		GlobalScope.launch { job.join() }

		return Result.success()
	}


}
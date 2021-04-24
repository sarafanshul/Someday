package com.projectdelta.someday.Util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.projectdelta.someday.Constant.DAY_VALUE
import com.projectdelta.someday.Data.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationWorker(appContext: Context, workerParams: WorkerParameters):
		Worker(appContext, workerParams) {

	override fun doWork(): Result {

		val job = GlobalScope.launch {
			NotificationUtil.data = AppDatabase.getDatabase(applicationContext).cardDataDao().getToday(
				DAY_VALUE)
			NotificationUtil.newNotification( applicationContext )
		}
		GlobalScope.launch { job.join() }

		return Result.success()
	}


}
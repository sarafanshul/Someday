package com.projectdelta.someday.Activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.projectdelta.someday.R
import com.projectdelta.someday.Constant.NOTIFICATION_INTERVAL
import com.projectdelta.someday.Fragment.SettingsFragment
import com.projectdelta.someday.Util.NotificationWorker
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() , SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme( R.style.Theme_CardVeiwApp )
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.pref_settings , SettingsFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    private val workTag = "notificationWork" ; private val dataTag = "notificationData"
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if(key!! == "notifications" ) {
            if (sharedPreferences?.getBoolean(key, false) == true) {
                startNotifications()
            } else {
                stopNotifications()
            }
        }
    }

    private fun startNotifications(){
        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(NOTIFICATION_INTERVAL , TimeUnit.MINUTES)
                .addTag(workTag)
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "periodicNotification",
                ExistingPeriodicWorkPolicy.REPLACE,
                notificationWorkRequest
        )
    }

    private fun stopNotifications(){
        WorkManager.getInstance().cancelAllWorkByTag(workTag)
        WorkManager.getInstance().cancelAllWorkByTag("periodicNotification")
    }

}

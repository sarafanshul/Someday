package com.projectdelta.someday.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.work.WorkManager
import com.projectdelta.someday.MainActivity
import com.projectdelta.someday.R
import com.projectdelta.someday.fragment.SettingsFragment
import kotlinx.android.synthetic.main.settings_activity.*
import kotlinx.android.synthetic.main.settings_activity.view.*

class SettingsActivity : AppCompatActivity() , SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        val RESULT_CODE_SETTINGS_NOT_CHANGED = 101
        val RESULT_CODE_SETTINGS_CHANGED = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme( R.style.Theme_CardVeiwApp )
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.pref_settings , SettingsFragment())
            .commit()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        setResult( RESULT_CODE_SETTINGS_CHANGED )
    }
}

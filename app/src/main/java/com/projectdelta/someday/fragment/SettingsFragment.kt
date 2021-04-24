package com.projectdelta.someday.Fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.projectdelta.someday.R

class SettingsFragment : PreferenceFragmentCompat() {

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.root_preferences, rootKey)

	}
}
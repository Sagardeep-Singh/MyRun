package com.cmpt276.myrun.ui.main

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.cmpt276.myrun.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
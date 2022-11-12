package com.cmpt276.myrun.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.cmpt276.myrun.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val editor =
            PreferenceManager.getDefaultSharedPreferences(requireContext()).edit()

        val unitPreference: ListPreference? = findPreference("unit_preference")
        unitPreference?.summaryProvider = Preference.SummaryProvider<ListPreference> { preference ->
            val unit = when (preference.value) {
                "Metric" -> "Kilometers"
                else -> "Miles"
            }

            editor.putString("distance_unit", unit).apply()

            unit
        }

        val webpagePreference: Preference? = findPreference("settings_webpage")
        webpagePreference?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.summary.toString())
            webpagePreference.intent = intent
            startActivity(intent)

            true
        }
    }
}
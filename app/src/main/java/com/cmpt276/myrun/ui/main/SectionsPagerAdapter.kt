package com.cmpt276.myrun.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmpt276.myrun.R

/**
 * A [PagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        val TAB_TITLES = arrayOf(
            R.string.tab_start,
            R.string.tab_history,
            R.string.tab_settings,
        )
    }
    override fun getItemCount(): Int {
        // Show 3 total pages.
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
       when (position) {
            1 -> return ExerciseHistoryFragment()
            2 -> return SettingsFragment()
           else -> return RecordExerciseFragment()
       }
    }
}
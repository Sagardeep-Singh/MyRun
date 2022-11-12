package com.cmpt276.myrun.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmpt276.myrun.R
import com.cmpt276.myrun.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getText(SectionsPagerAdapter.TAB_TITLES[position])
        }.attach()
    }

    /**
     * A [PagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

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
            return when (position) {
                1 -> HistoryFragment()
                2 -> SettingsFragment()
                else -> StartFragment()
            }
        }
    }
}
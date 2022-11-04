package com.cmpt276.myrun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmpt276.myrun.databinding.ActivityMainBinding
import com.cmpt276.myrun.ui.main.SectionsPagerAdapter
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
}
package com.cmpt276.myrun.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cmpt276.myrun.databinding.ActivityGpsEntryBinding

class GpsEntryActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGpsEntryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Map"

        setContentView(binding.root)
    }

    fun onSaveClick(view: View) {
        finish()
    }

    fun onCancelClick(view: View) {
        finish()
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, GpsEntryActivity::class.java)
    }
}
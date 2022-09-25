package com.cmpt276.myrun

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cmpt276.myrun.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onChangeClicked(view: View) {

        Toast.makeText(this, "Change Clicked", Toast.LENGTH_SHORT).show()
    }

    fun onSaveClicked(view: View){
        Toast.makeText(this, "Save Clicked", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun onCancelClicked(view: View){
        Toast.makeText(this, "Cancel Clicked", Toast.LENGTH_SHORT).show()
        this.finish()
    }
}
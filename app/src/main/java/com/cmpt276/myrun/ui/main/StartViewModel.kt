package com.cmpt276.myrun.ui.main

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel : ViewModel() {
    val inputTypeListener = object : OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            onInputTypeSelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            onInputTypeSelected(0)
        }
    }

    val activityTypeListener = object : OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            onActivityTypeSelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            onActivityTypeSelected(0)
        }
    }

    val inputTypeArray = arrayOf("Manual Entry", "GPS", "Automatic")
    val activityTypeArray = arrayOf(
        "Running",
        "Walking",
        "Cycling",
        "Hiking",
        "Downhill Skiing",
        "Cross-Country Skiing",
        "Snowboarding",
        "Skating",
        "Swimming",
        "Mountain Biking",
        "Wheelchair",
        "Elliptical",
        "Other"
    )

    fun onInputTypeSelected(position: Int) {
        _inputType.postValue(inputTypeArray[position])
    }

    fun onActivityTypeSelected(position: Int) {
        _activityType.postValue( activityTypeArray[position])
    }

    private val _activityType = MutableLiveData<String>()
    val activityType: LiveData<String>
        get() = _activityType

    private val _inputType = MutableLiveData<String>()
    val inputType: LiveData<String>
        get() = _inputType

}
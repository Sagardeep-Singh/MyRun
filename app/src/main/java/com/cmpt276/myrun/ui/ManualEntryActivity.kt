package com.cmpt276.myrun.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cmpt276.myrun.databinding.ActivityManualEntryBinding
import com.cmpt276.myrun.model.*
import java.text.SimpleDateFormat


class ManualEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManualEntryBinding
    private lateinit var exercise: Exercise
    private lateinit var viewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManualEntryBinding.inflate(layoutInflater)

        val database = ExerciseDatabase.getInstance(this)
        val repository = ExerciseRepository(database.exerciseDao())
        val viewModelFactory = ExerciseViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[ExerciseViewModel::class.java]

        setupEmptyExercise()
        setupClickListeners()

        title = "Manual Entry"

        // calling the action bar
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupEmptyExercise() {
        val inputType = intent.getStringExtra("inputType")
        val activityType = intent.getStringExtra("activityType")

        exercise = Exercise().apply {
            this.inputType = inputType
            this.activityType = activityType
        }
    }

    private fun setupClickListeners() {
        binding.caloriesTextview.setOnClickListener() {
            val dialog =
                MyInputDialog("Calories", InputType.TYPE_CLASS_NUMBER) { text ->
                    exercise.calories = text.toInt()
                }
            dialog.show(supportFragmentManager, "CaloriesDialogFragment")
        }

        binding.distanceTextview.setOnClickListener() {
            val dialog =
                MyInputDialog("Distance", InputType.TYPE_NUMBER_FLAG_DECIMAL) { text ->
                    exercise.distance = text.toDouble()
                }
            dialog.show(supportFragmentManager, "DistanceDialogFragment")
        }

        binding.durationTextview.setOnClickListener() {
            val dialog =
                MyInputDialog("Duration", InputType.TYPE_CLASS_NUMBER) { text ->
                    exercise.duration = text.toDouble()
                }
            dialog.show(supportFragmentManager, "DurationDialogFragment")
        }

        binding.heartRateTextview.setOnClickListener() {
            val dialog =
                MyInputDialog("Heart Rate", InputType.TYPE_CLASS_NUMBER) { text ->
                    exercise.heartRate = text.toInt()
                }
            dialog.show(supportFragmentManager, "HeartRateDialogFragment")
        }

        binding.commentTextview.setOnClickListener() {
            val dialog =
                MyInputDialog("Comment", InputType.TYPE_NUMBER_FLAG_DECIMAL) { text ->
                    exercise.comment = text
                }
            dialog.show(supportFragmentManager, "ClimbDialogFragment")
        }

        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        binding.dateTextview.setOnClickListener() {
            val dialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                exercise.date = datePartsToString(year, month, dayOfMonth)
            }, year, month, day)

            dialog.show()
        }

        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)
        val seconds = calendar.get(java.util.Calendar.SECOND)

        binding.timeTextview.setOnClickListener() {
            val dialog = TimePickerDialog(this, { _, hour, minute ->
                exercise.time = "$hour:$minute:00"
            }, hour, minute, true)

            dialog.show()
        }

        exercise.date = datePartsToString(year, month, day)
        exercise.time = "$hour:$minute:$seconds"

    }

    private fun datePartsToString(year: Int, month: Int, day: Int): String? {
        val inputPattern = "yyyy-MM-dd"
        val inputFormat = SimpleDateFormat(inputPattern, java.util.Locale.getDefault())
        val outputFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, java.util.Locale.getDefault())

        val date = "$year-${month + 1}-$day"
        val dateObj = inputFormat.parse(date)
        return dateObj?.let { outputFormat.format(it) }
    }

    fun onSaveButtonClicked(view: View) {
        viewModel.insert(exercise)
        finish()
    }

    class MyInputDialog(
        private val title: String,
        private val type: Int,
        private val inputListener: (String) -> Unit
    ) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            val editText = EditText(requireContext()).apply {
                hint = title
                inputType = type
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.MATCH_PARENT,
                    ViewGroup.MarginLayoutParams.MATCH_PARENT
                ).apply {
                    marginStart = 50
                    marginEnd = 50
                }
            }


            return AlertDialog.Builder(requireContext()).setTitle(title).setView(editText)
                .setPositiveButton("OK") { _, _ ->
                    inputListener(editText.text.toString())
                }.setNegativeButton("Cancel") { _, _ -> }.create()
        }
    }

    companion object {
        private const val TAG = "ManualEntryActivity"
        private const val OUTPUT_DATE_FORMAT = "dd-MMM-yyyy"

        fun getIntent(context: Context, inputType: String, activityType: String) =
            Intent(context, ManualEntryActivity::class.java).apply {
                putExtra("inputType", inputType)
                putExtra("activityType", activityType)
            }
    }
}
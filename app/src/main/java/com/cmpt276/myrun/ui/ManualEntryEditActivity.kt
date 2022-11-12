package com.cmpt276.myrun.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.cmpt276.myrun.R
import com.cmpt276.myrun.databinding.ActivityManualEntryEditBinding
import com.cmpt276.myrun.model.*
import kotlin.math.roundToInt


class ManualEntryEditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityManualEntryEditBinding.inflate(layoutInflater) }
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var exercise: Exercise

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val exerciseId = intent.getLongExtra(EXERCISE_ID, -1)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val distanceUnits = preferences.getString("distance_units", "km")!!

        if (exerciseId == -1L) {
            finish()
        }

        val database = ExerciseDatabase.getInstance(this)
        val repository = ExerciseRepository(database.exerciseDao())
        val viewModelFactory = ExerciseViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ExerciseViewModel::class.java]


        viewModel.allExercises.observe(this) { exercises ->
            exercises.find { it.id == exerciseId }?.let { exercise ->
                this.exercise = exercise
                binding.inputTypeEditText.setText(exercise.inputType)
                binding.activityTypeEditText.setText(exercise.activityType)
                binding.caloriesEditText.setText(getString(R.string.fmt_calories).format(exercise.calories))
                binding.distanceEditText.setText(
                    getString(R.string.fmt_distance).format(
                        exercise.distance,
                        distanceUnits
                    )
                )
                binding.durationEditText.setText(getString(R.string.fmt_duration).format((exercise.duration * 60).roundToInt()))
                binding.dateTimeEditText.setText(
                    getString(R.string.fmt_datetime).format(
                        exercise.date,
                        exercise.time
                    )
                )
                binding.heartRateEditText.setText(getString(R.string.fmt_heart_rate).format(exercise.heartRate))
                binding.commentEditText.setText(exercise.comment)
            }
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_manual_entry_edit, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.delete(exercise)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXERCISE_ID = "exerciseId"

        fun getIntent(context: Context, id: Long): Intent {
            return Intent(context, ManualEntryEditActivity::class.java).apply {
                putExtra(EXERCISE_ID, id)
            }
        }
    }


}
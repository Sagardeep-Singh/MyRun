package com.cmpt276.myrun.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpt276.myrun.R
import com.cmpt276.myrun.model.*
import kotlin.math.roundToInt

/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment() {

    private lateinit var adapter: ExerciseRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_exercise_record_history_list, container, false)

        val database = ExerciseDatabase.getInstance(requireContext())
        val repository = ExerciseRepository(database.exerciseDao())
        val viewModelFactory = ExerciseViewModelFactory(repository)
        val viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[ExerciseViewModel::class.java]

        // Set the adapter
        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(context)

            adapter = ExerciseRecyclerViewAdapter(arrayListOf())

            view.adapter = adapter

            viewModel.allExercises.observe(viewLifecycleOwner) {
                adapter.updateList(it)
            }

        }

        return view
    }

    /**
     * [RecyclerView.Adapter] that can display an [Exercise].
     */
    inner class ExerciseRecyclerViewAdapter(
        private var values: List<Exercise>
    ) : RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.exercise_title)
            val summary: TextView = view.findViewById(R.id.exercise_summary)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_exercise_record_history, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.title.text =
                "%s: %s, %s %s".format(item.inputType, item.activityType, item.time, item.date)
            holder.summary.text = "%s %s, %s".format(
                item.distance,
                "kilometres",
                durationToString(item.duration),
            )
        }

        private fun durationToString(duration: Double): String {
            val durationInSeconds = (duration * 60L).roundToInt()
            val minutes = durationInSeconds / 60
            val seconds = durationInSeconds % 60
            return "%02d minutes %02d seconds".format(minutes, seconds)
        }

        override fun getItemCount(): Int = values.size
        fun updateList(it: List<Exercise>?) {
            adapter.values = it!!
            adapter.notifyDataSetChanged()
        }

    }
}
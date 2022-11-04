package com.cmpt276.myrun.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpt276.myrun.R
import com.cmpt276.myrun.ui.main.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ExerciseHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_exercise_record_history_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = ExerciseRecordRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }
}
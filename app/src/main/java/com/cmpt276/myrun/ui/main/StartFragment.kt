package com.cmpt276.myrun.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmpt276.myrun.R
import com.cmpt276.myrun.databinding.FragmentRecordExerciseBinding
import com.cmpt276.myrun.ui.GpsEntryActivity
import com.cmpt276.myrun.ui.ManualEntryActivity

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this)[StartViewModel::class.java]

        val binding = FragmentRecordExerciseBinding.inflate(inflater, container, false)

        binding.inputTypeSpinner.adapter = MySpinnerAdapter(
            requireContext(),
            R.layout.spinner_item,
            viewModel.inputTypeArray
        )
        binding.inputTypeSpinner.onItemSelectedListener = viewModel.inputTypeListener

        binding.activityTypeSpinner.adapter = MySpinnerAdapter(
            requireContext(),
            R.layout.spinner_item,
            viewModel.activityTypeArray
        )
        binding.activityTypeSpinner.onItemSelectedListener = viewModel.activityTypeListener

        binding.startButton.setOnClickListener() {
            onStartButtonClicked()
        }
        return binding.root
    }

    private fun onStartButtonClicked() {

        val intent =
            when (viewModel.inputType.value) {
                viewModel.inputTypeArray[1] -> GpsEntryActivity.getIntent(requireContext())
                viewModel.inputTypeArray[2] -> GpsEntryActivity.getIntent(requireContext())
                else -> ManualEntryActivity.getIntent(
                    requireContext(), viewModel.inputType.value!!,
                    viewModel.activityType.value!!
                )
            }

        startActivity(intent)
    }

    inner class MySpinnerAdapter(
        val context: Context,
        private val simpleSpinnerItem: Int,
        private val activityTypeArray: Array<String>
    ) :
        BaseAdapter() {
        override fun getCount(): Int {
            return activityTypeArray.size
        }

        override fun getItem(p0: Int): Any {
            return activityTypeArray[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, existingView: View?, p2: ViewGroup?): View {
            val view = existingView ?: LayoutInflater.from(context).inflate(
                simpleSpinnerItem,
                p2,
                false
            )

            val textView: TextView = view.findViewById(R.id.spinner_item_text_view)

            textView.text = activityTypeArray[p0]

            return view
        }

    }
}
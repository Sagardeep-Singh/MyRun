package com.cmpt276.myrun.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {
    val allExercises: LiveData<List<Exercise>> = repository.allExercises.asLiveData()

    fun insert(exercise: Exercise) {
        repository.insert(exercise)
    }

    fun delete(exercise: Exercise) {
        repository.delete(exercise)
    }

    fun update(exercise: Exercise) {
        repository.update(exercise)
    }

    fun deleteAll() {
        repository.deleteAll()
    }
}
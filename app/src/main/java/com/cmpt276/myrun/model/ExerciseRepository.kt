package com.cmpt276.myrun.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    val allExercises: Flow<List<Exercise>> = exerciseDao.getAll()

    fun insert(exercise: Exercise) {
        CoroutineScope(IO).launch {
            exerciseDao.insert(exercise)
        }
    }

    fun delete(exercise: Exercise) {
        CoroutineScope(IO).launch {
            exerciseDao.delete(exercise)
        }
    }

    fun update(exercise: Exercise) {
        CoroutineScope(IO).launch {
            exerciseDao.update(exercise)
        }
    }

    fun deleteAll() {
        CoroutineScope(IO).launch {
            exerciseDao.deleteAll()
        }
    }

}
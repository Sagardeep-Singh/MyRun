package com.cmpt276.myrun.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    fun getAll(): Flow<List<Exercise>>

    @Query("DELETE FROM exercise")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Update
    suspend fun update(exercise: Exercise)
}
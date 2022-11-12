package com.cmpt276.myrun.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "input_type")
    var inputType: String? = null,

    @ColumnInfo(name = "activity_type")
    var activityType: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name = "time")
    var time: String? = null,

    @ColumnInfo(name = "heart_rate")
    var heartRate: Int = 0,

    var duration: Double = 0.0,
    var distance: Double = 0.0,
    var calories: Int = 0,
    var comment: String = ""


)

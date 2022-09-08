package com.ebenezer.gana.aisattendance.data.models

data class AdminAttendance(
    val dayOfTheWeek: String,
    val attendance: MutableList<Attendance>
)
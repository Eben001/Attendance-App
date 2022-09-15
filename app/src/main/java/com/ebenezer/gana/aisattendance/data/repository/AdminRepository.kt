package com.ebenezer.gana.aisattendance.data.repository

import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.utils.Constants
import com.ebenezer.gana.aisattendance.utils.UiText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class AdminRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    /**
     * Creates a new attendance from firestore.
     * @param newAttendanceSheet It is used to display the current day which will be displayed on the Start screen
     * @param onSuccess Message to return when task is successful
     * @param onFailure Message to return when task fails
     */
    fun createAttendance(
        newAttendanceSheet: Day,
        onSuccess: (UiText) -> Unit,
        onFailure: (UiText) -> Unit
    ) {

        val newSheet = Day(day = newAttendanceSheet.day)

        /**
         * Here, we are creating a new firestore document in the "days" collection. Inside the
         * document, we are also creating a "DAY" object passing in today's date and timeStamp.
         * We then create an inner collection to store list of each day's attendance
         */
        firestore.runTransaction { transaction ->
            val documentRef = firestore.collection(Constants.DAYS).document()
            transaction[documentRef] = newSheet


            firestore.collection(Constants.ATTENDANCE)
                .document()
        }
            .addOnSuccessListener {
                onSuccess(UiText.StringResource(R.string.success))

            }

            .addOnFailureListener {
                onFailure(UiText.DynamicString(it.message!!))

            }

    }

    /**
     * Gets the list of attendance as created by the admin
     * @param result Returns a list of each day's attendance to be displayed on the Admin's Screen
     */
    fun getListOfAttendances(result: (List<Day>) -> Unit) {
        firestore.collection(Constants.DAYS)
            .orderBy(Constants.TIMESTAMP, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { document ->
                val attendanceList = mutableListOf<Day>()
                val documents = document.documents

                documents.forEach {
                    val day = it.toObject(Day::class.java)
                    if (day != null) {
                        day.id = it.id
                        attendanceList.add(day)
                    }
                }
                result(attendanceList)

            }
    }
}
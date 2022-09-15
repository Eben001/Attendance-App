package com.ebenezer.gana.aisattendance.data.repository

import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.utils.Constants
import com.ebenezer.gana.aisattendance.utils.UiText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class StaffListRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    /**
     * Gets the specific day's attendance from firestore
     * @param id the Id of the document to get from the 'days' collection
     * @param result used to return a list of the attendance with the passed id
     */
    fun getAttendanceList(id: String, result: (List<Attendance>) -> Unit) {
        firestore.collection(Constants.DAYS)
            .document(id)
            .collection(Constants.ATTENDANCE)
            .orderBy(Constants.TIMESTAMP, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { document ->
                val attendanceList = mutableListOf<Attendance>()
                val documents = document.documents

                documents.forEach {
                    // we want to get the id of each document
                    val attendance = it.toObject(Attendance::class.java)
                    if (attendance != null) {
                        attendance.id = it.id
                        attendanceList.add(attendance)
                    }
                }
                result(attendanceList)

            }
            .addOnFailureListener {

            }
    }

    /**
     * Adds a new attendee to the attendance collection for each day.
     * @param newAttendance an objects that represents a single attendee to be recorded
     * @param onSuccess receives a success message
     * @param onFailure receives a failure message
     */
    fun addNewAttendee(
        newAttendance: Attendance, onSuccess: (UiText) -> Unit,
        onFailure: (UiText) -> Unit
    ) {

        val attendance = Attendance(
            id = newAttendance.id,
            userId = getCurrentUserId(),
            name = newAttendance.name,
            time = newAttendance.time,
            timeStamp = newAttendance.timeStamp
        )
        firestore.collection(Constants.DAYS)
            .document(newAttendance.id!!)
            .collection(Constants.ATTENDANCE)
            .document()
            .set(attendance, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess(UiText.StringResource(R.string.success))
            }
            .addOnFailureListener {
                onFailure(UiText.DynamicString(it.message!!))
            }
    }

    /**
     * Gets the current logged in user id; no use as of now because
     * we haven't implemented authentication yet
     */
    private fun getCurrentUserId(): String {
        var currentUserID = ""

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            currentUserID = it.uid
        }
        return currentUserID
    }
}
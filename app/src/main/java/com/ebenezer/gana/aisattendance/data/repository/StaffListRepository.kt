package com.ebenezer.gana.aisattendance.data.repository

import android.util.Log
import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Attendance
import com.ebenezer.gana.aisattendance.utils.Constants
import com.ebenezer.gana.aisattendance.utils.UiText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

private const val TAG = "StaffListRepository"

class StaffListRepository @Inject constructor(private val firestore: FirebaseFirestore) {

/*
    fun getAttendanceList(result: (List<Attendance>) -> Unit) {
        firestore.collection(Constants.ATTENDANCE)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
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
                Log.d(TAG, "getAttendanceList: ${it.localizedMessage}")
            }

    }
*/

    fun getAttendanceList(id: String, result: (List<Attendance>) -> Unit) {

        firestore.collection(Constants.DAYS)
            .document(id)
            .collection(Constants.ATTENDANCE)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
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
                Log.d(TAG, "getAttendanceList:${attendanceList.size}")
                result(attendanceList)

            }
            .addOnFailureListener {
                Log.d(TAG, "getAttendanceList: ${it.localizedMessage}")
            }

    }


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


        /*  firestore.collection(Constants.DAYS)
              .document()
              .set(attendance, SetOptions.merge())
              .addOnSuccessListener {
                  onSuccess(UiText.StringResource(R.string.success))
              }
              .addOnFailureListener {
                  onFailure(UiText.DynamicString(it.message!!))
              }*/


    }

    /**
     * Gets the current logged in user id
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
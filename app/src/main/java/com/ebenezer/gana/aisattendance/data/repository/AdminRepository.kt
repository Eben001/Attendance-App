package com.ebenezer.gana.aisattendance.data.repository

import com.ebenezer.gana.aisattendance.R
import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.utils.Constants
import com.ebenezer.gana.aisattendance.utils.UiText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

private const val TAG = "AdminRepository"
class AdminRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun createAttendance(
        newAttendanceSheet: Day,
        onSuccess: (UiText) -> Unit,
        onFailure: (UiText) -> Unit
    ) {

        val newSheet = Day(day = newAttendanceSheet.day)

        firestore.runTransaction { transaction ->
            val documentRef = firestore.collection(Constants.DAYS).document()
            transaction[documentRef] = newSheet
            null

            firestore.collection(Constants.ATTENDANCE)
                .document()
        }
            .addOnSuccessListener {
                onSuccess(UiText.StringResource(R.string.success))

            }

            .addOnFailureListener {
                onFailure(UiText.DynamicString(it.message!!))

            }


    /*    firestore.collection(Constants.DAYS)
            .document()
            .set(newSheet, SetOptions.merge())
*/
        /*    .collection(Constants.ATTENDANCE)
            .document()
            .addOnSuccessListener {
                onSuccess(UiText.StringResource(R.string.success))

            }

            .addOnFailureListener {
                onFailure(UiText.DynamicString(it.message!!))

            }*/
    }

    fun getTodayAttendanceList(result: (List<Day>) -> Unit) {
       firestore.collection("days")
           .orderBy("timeStamp", Query.Direction.DESCENDING)
           .get()
           .addOnSuccessListener {document ->
               val attendanceList = mutableListOf<Day>()
               val documents = document.documents

               documents.forEach {
                   val day = it.toObject(Day::class.java)
                   if(day !=null){
                       day.id = it.id
                       attendanceList.add(day)
                   }
               }
               result(attendanceList)

           }
    }
}
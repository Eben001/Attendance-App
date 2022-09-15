package com.ebenezer.gana.aisattendance.data.repository

import com.ebenezer.gana.aisattendance.data.models.Day
import com.ebenezer.gana.aisattendance.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class StartScreenRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    fun getAvailableAttendance(result:(Day) -> Unit){
        firestore.collection(Constants.DAYS)
            .orderBy(Constants.TIMESTAMP, Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { document ->
                val documents = document.documents
                documents.forEach {
                    val day = it.toObject(Day::class.java)
                    if(day !=null){
                        day.id = it.id
                        result(day)

                    }
                }
            }
    }

}
package com.ebenezer.gana.aisattendance.data.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Attendance(
    var id: String? = "",
    var userId: String? = "",
    val name: String? = "",
    val time: String? = "",
    val timeStamp: Timestamp = Timestamp.now()
) : Parcelable
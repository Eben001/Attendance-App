package com.ebenezer.gana.aisattendance.data.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize


@IgnoreExtraProperties
@Parcelize
data class Day(
    var id: String? = "",
    val day: String? = "",
    val signedBy:String? = "",
    val timeStamp: Timestamp = Timestamp.now()
):Parcelable
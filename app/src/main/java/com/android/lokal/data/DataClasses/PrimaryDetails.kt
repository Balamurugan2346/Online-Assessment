package com.android.lokal.data.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PrimaryDetails(
    val Place: String,
    val Salary: String,
    val Job_Type: String? = null,
    val Experience: String,
    val Fees_Charged: String,
    val Qualification: String
)  : Parcelable
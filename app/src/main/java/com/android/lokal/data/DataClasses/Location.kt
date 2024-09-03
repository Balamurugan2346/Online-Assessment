package com.android.lokal.data.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Location(
    val id: Int,
    val locale: String,
    val state: Int
) : Parcelable
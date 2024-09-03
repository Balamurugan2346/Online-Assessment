package com.android.lokal.data.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Creative(
    val file: String,
    val thumb_url: String,
    val creative_type: Int
) : Parcelable
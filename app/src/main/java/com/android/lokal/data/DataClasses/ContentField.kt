package com.android.lokal.data.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContentField(
    val field_key: String,
    val field_name: String,
    val field_value: String
) : Parcelable
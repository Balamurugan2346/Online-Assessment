package com.android.lokal.data.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class JobTag(
    val value: String,
    val bg_color: String,
    val text_color: String
) : Parcelable
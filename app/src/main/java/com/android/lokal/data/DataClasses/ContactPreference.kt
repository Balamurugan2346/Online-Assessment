package com.android.lokal.data.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ContactPreference(
    val preference: Int,
    val whatsapp_link: String,
    val preferred_call_start_time: String,
    val preferred_call_end_time: String
) : Parcelable
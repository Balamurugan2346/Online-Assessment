package com.android.lokal.data.DataClasses

import kotlinx.serialization.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
@Serializable
data class FeeDetails(
    val V3: List<String?>
) : Parcelable
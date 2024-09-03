package com.android.lokal.data.DataClasses

import kotlinx.serialization.Serializable

@Serializable
data class JobListingResponse(
    val results: ArrayList<JobListing>
)
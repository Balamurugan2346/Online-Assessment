package com.android.lokal.ui.Adapter

import com.android.lokal.data.DataClasses.JobListing

interface JobListListener {
    fun onLoadMore()
    fun onClick(job:JobListing)
    fun saveJobFromList(job:JobListing)
}

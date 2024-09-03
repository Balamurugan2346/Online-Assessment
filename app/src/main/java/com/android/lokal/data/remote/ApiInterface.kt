package com.android.lokal.data.remote

import com.android.lokal.data.DataClasses.JobListingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/common/jobs")
    suspend fun getAllJobs(@Query("page") page : Int): Response<JobListingResponse>
}
package com.android.lokal.data.remote.repository

import com.android.lokal.data.DataClasses.JobListing
import com.android.lokal.data.remote.ApiInterface
import com.android.lokal.data.remote.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchJobRepository {

    sealed class Result {
        data class Success(val jobs: ArrayList<JobListing>) : Result()
        data class Error(val message: String) : Result()
    }

    suspend fun getAllJobs(page:Int): Result {
        return withContext(Dispatchers.IO) {
            try {
                val service = ServiceBuilder.createService(ApiInterface::class.java)
                val response = service.getAllJobs(page)

                if (response.isSuccessful) {
                    val content = response.body()
                    val jobs = content?.results ?: arrayListOf()
                    Result.Success(jobs)
                } else {
                    Result.Error("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Result.Error("Exception: ${e.message ?: "Unknown error"}")
            }
        }
    }
}
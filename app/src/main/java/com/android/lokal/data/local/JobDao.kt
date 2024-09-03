package com.android.lokal.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.lokal.data.DataClasses.JobListing

@Dao
interface JobDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveJob(user : JobListing)

    @Query("SELECT * FROM job_table")
    fun getSavedJob(): LiveData<List<JobListing>>

    @Query("SELECT * FROM job_table WHERE id = :jobId")
    suspend fun getJobById(jobId: Int): JobListing?

    @Query("DELETE FROM job_table")
    suspend fun deleteAllJobs()

    @Transaction
    suspend fun insertJobIfNotExists(job: JobListing) {
        val existingJob = getJobById(job.id)
        if (existingJob == null) {
            saveJob(job)
        }
    }

    @Query("DELETE  FROM job_table WHERE id = :jobId")
    suspend fun deleteJobById(jobId: Int) : Int

}
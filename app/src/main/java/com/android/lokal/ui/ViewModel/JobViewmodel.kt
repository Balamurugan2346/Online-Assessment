package com.android.lokal.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.lokal.data.DataClasses.JobListing
import com.android.lokal.data.local.JobDao
import com.android.lokal.data.remote.repository.SearchJobRepository
import kotlinx.coroutines.launch

class JobViewModel(private val repository: SearchJobRepository, private val dao: JobDao) : ViewModel() {

    var currentPage = 1
    private val maxPages = 3

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _jobsLiveData = MutableLiveData<ArrayList<JobListing>>()
    val jobsLiveData: LiveData<ArrayList<JobListing>> get() = _jobsLiveData

    private val _jobLocalData = dao.getSavedJob()

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData


    fun setIsLoading(value:Boolean){
        _isLoading.value = value
    }

    fun fetchJobs(page: Int) {
        if (currentPage > maxPages) {
            return
        }

        viewModelScope.launch {
            when (val result = repository.getAllJobs(page)) {
                is SearchJobRepository.Result.Success -> {
                    val filteredJobs = result.jobs.filter { it.type != 1040 }

                    val currentJobs = _jobsLiveData.value ?: arrayListOf()
                    currentJobs.addAll(filteredJobs)
                    _jobsLiveData.postValue(currentJobs)
                    if (filteredJobs.isNotEmpty()) {
                        currentPage++
                    }
                }
                is SearchJobRepository.Result.Error -> {
                    _errorLiveData.postValue(result.message)
                }
            }
        }
    }


    fun saveLocalJob(jobListing: JobListing) {
        viewModelScope.launch {
            dao.insertJobIfNotExists(jobListing)
        }
    }

    fun getSavedJobs(): LiveData<List<JobListing>> {
        return _jobLocalData
    }

    fun deleteAllJobs() {
        viewModelScope.launch {
            dao.deleteAllJobs()
        }
    }

  suspend  fun getJobById(jobId: Int) : JobListing? {
      return  dao.getJobById(jobId)
    }


    suspend fun deleteJobByID(jobId:Int) : Int{
            val rowsDeleted = dao.deleteJobById(jobId)
             return rowsDeleted
    }



    fun getBookmarkedJobs(): LiveData<List<JobListing>> {
        return dao.getSavedJob()
    }
}


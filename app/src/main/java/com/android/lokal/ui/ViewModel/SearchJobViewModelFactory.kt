package com.android.lokal.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.lokal.data.local.JobDao
import com.android.lokal.data.remote.repository.SearchJobRepository

class SearchJobViewModelFactory(private val repository: SearchJobRepository, private val dao: JobDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobViewModel::class.java)) {
            return JobViewModel(repository,dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

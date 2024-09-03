package com.android.lokal.ui.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lokal.R
import com.android.lokal.data.DataClasses.JobListing
import com.android.lokal.data.local.JobDatabase
import com.android.lokal.data.remote.IsInternetAvailable
import com.android.lokal.data.remote.repository.SearchJobRepository
import com.android.lokal.ui.Adapter.JobListAdapter
import com.android.lokal.ui.Adapter.JobListListener
import com.android.lokal.ui.JobDetailsActivity
import com.android.lokal.ui.ViewModel.JobViewModel
import com.android.lokal.ui.ViewModel.SearchJobViewModelFactory
import kotlinx.coroutines.launch


class JobsFragment : Fragment(),JobListListener {
    private lateinit var viewModel: JobViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JobListAdapter
    private lateinit var jobList : ArrayList<JobListing>
   private lateinit var progressBar: ProgressBar
   private lateinit var internetText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_jobs, container, false)
        internetText = view.findViewById(R.id.noInternetText)

        val repository = SearchJobRepository()

        val dao = JobDatabase.getDatabase(requireContext()).jobDao()
        val factory = SearchJobViewModelFactory(repository,dao)


        viewModel = ViewModelProvider(this, factory).get(JobViewModel::class.java)
        setupRecyclerView(view)
        progressBar = view.findViewById(R.id.progressBar)

        viewModel.isLoading.observe(viewLifecycleOwner){isLoading->
            if(isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }

        val isInternet = IsInternetAvailable.isInternetAvailable(requireContext())
        if (isInternet){
            viewModel.setIsLoading(true)
            viewModel.fetchJobs(1)
        }else{
            internetText.visibility = View.VISIBLE
            viewModel.setIsLoading(false)
        }


        viewModel.jobsLiveData.observe(viewLifecycleOwner) { jobs ->
            adapter.submitList(jobs)
            viewModel.setIsLoading(false)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(),error.toString(), Toast.LENGTH_SHORT).show()
            viewModel.setIsLoading(false)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (isInternet) {
                        viewModel.setIsLoading(true)
                        viewModel.fetchJobs(viewModel.currentPage)
                        viewModel.setIsLoading(false)
                    } else {
                        internetText.visibility = View.VISIBLE
                        viewModel.setIsLoading(false)
                    }
                }
            }
        })
        return view
    }

    private fun setupRecyclerView(view:View){
        recyclerView = view.findViewById(R.id.job_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobList = arrayListOf()
        adapter = JobListAdapter(jobList,requireContext(),this,viewModel)
        recyclerView.adapter = adapter
    }

    override fun onLoadMore() {}

    override fun onClick(job: JobListing) {
         val intent = Intent(requireContext(), JobDetailsActivity::class.java)
        intent.putExtra("job_listing", job)
        startActivity(intent)
    }

    override fun saveJobFromList(job: JobListing) {
        lifecycleScope.launch {
            try {
                val id = viewModel.getJobById(job.id)
                if (id != null) {
                    Toast.makeText(requireContext(), "Job already saved", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.saveLocalJob(job)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }





}
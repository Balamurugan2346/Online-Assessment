package com.android.lokal.ui.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
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
   private var pastVisibleItems = 0
   private var visibleItemCount = 0
    private var totalItemCount = 0
    private var mLayoutManager : LinearLayoutManager? =null

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
            Log.d("jobsLoadedInTheScreen","loaded ${jobs.size}")
            adapter.submitList(jobs)
            viewModel.setIsLoading(false)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(),error.toString(), Toast.LENGTH_SHORT).show()
            viewModel.setIsLoading(false)
        }


        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("fggdgdgdgvdg", "dx: $dx, dy: $dy")
                if (dy > 0) {
                    visibleItemCount = mLayoutManager!!.childCount
                    totalItemCount = mLayoutManager!!.itemCount
                    pastVisibleItems = mLayoutManager!!.findFirstVisibleItemPosition()
                    Log.d("dfgrfctyhrthyhr", "Visible Item Count: $visibleItemCount, Total Item Count: $totalItemCount, Past Visible Items: $pastVisibleItems")
                   val isNetworkEnabled = IsInternetAvailable.isInternetAvailable(requireContext())
                    if (isNetworkEnabled && !viewModel.isLoading.value!! && (visibleItemCount + pastVisibleItems >= totalItemCount)) {
                        if (viewModel.currentPage <= viewModel.maxPages) {
                            viewModel.setIsLoading(true)
                            viewModel.fetchJobs(viewModel.currentPage)
                            Log.d("xgfctrhtycfutv6f","current page fetched${viewModel.currentPage}")
                        } else {
                            Toast.makeText(requireContext(), "No more items", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Log.d("dhdhdhd","not greater than 0")
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("dhdhdhdhdhgf", "Scroll state changed: $newState")
            }

        })



        return view
    }

    private fun setupRecyclerView(view:View){
        recyclerView = view.findViewById(R.id.job_list_recyclerview)
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        jobList = arrayListOf()
        adapter = JobListAdapter(jobList,requireContext(),this,viewModel)
       recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
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
package com.android.lokal.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lokal.R
import com.android.lokal.data.DataClasses.JobListing
import com.android.lokal.data.local.JobDatabase
import com.android.lokal.data.remote.repository.SearchJobRepository
import com.android.lokal.ui.Adapter.BookMarkAdapter
import com.android.lokal.ui.Adapter.BookMarkListener
import com.android.lokal.ui.ViewModel.JobViewModel
import com.android.lokal.ui.ViewModel.SearchJobViewModelFactory
import kotlinx.coroutines.launch


class BookMarkFragment : Fragment() , BookMarkListener  {

    private lateinit var viewModel: JobViewModel
    private lateinit var jobAdapter: BookMarkAdapter
    private lateinit var noBookMark_text : TextView
    private lateinit var listLobs : ArrayList<JobListing>
    private lateinit var deleteButton : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_mark_fragments, container, false)
        listLobs = arrayListOf()
        noBookMark_text = view.findViewById(R.id.no_book_mark_text)
        deleteButton = view.findViewById(R.id.delete_BookMarks)
        deleteButton.setOnClickListener {
            viewModel.deleteAllJobs()
        }
        val repository = SearchJobRepository()
        val dao = JobDatabase.getDatabase(requireContext()).jobDao()
        val factory = SearchJobViewModelFactory(repository, dao)

        viewModel = ViewModelProvider(this, factory).get(JobViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.bookmark_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobAdapter = BookMarkAdapter(listLobs,requireContext(),this)
        recyclerView.adapter = jobAdapter

        viewModel.getSavedJobs().observe(viewLifecycleOwner) { savedJobs ->
            if (savedJobs.isEmpty()) {
                noBookMark_text.visibility = View.VISIBLE
            } else {
                noBookMark_text.visibility = View.GONE
            }
            val uniqueJobs = savedJobs.distinctBy { it.id }
            listLobs.clear()
            listLobs.addAll(uniqueJobs)
            jobAdapter.submitList(listLobs)
        }


        return view
    }

    override fun onDelete(job: JobListing?) {
        lifecycleScope.launch {
            if(job!=null){
                try {
                    val id = job.id
                     val rowsDeleted =   viewModel.deleteJobByID(id)
                    if(rowsDeleted>0){
                        Toast.makeText(requireContext(),"bookmark removed",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Job deletion failed", Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"unable to delete",Toast.LENGTH_SHORT).show()
            }
        }
    }


}

package com.android.lokal.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.lokal.R
import com.android.lokal.data.DataClasses.JobListing
import com.android.lokal.ui.ViewModel.JobViewModel
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class JobListAdapter(var jobList: ArrayList<JobListing>,val context: Context,val onClick : JobListListener,val viewModel:JobViewModel) : RecyclerView.Adapter<JobListAdapter.JobViewHolder>() {


    init {
        viewModel.getBookmarkedJobs().observeForever { bookmarkedJobs ->
            updateBookmarkStatus(bookmarkedJobs)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateBookmarkStatus(bookmarkedJobs: List<JobListing>) {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card,parent,false)
        return JobViewHolder(view)
    }

    override fun getItemCount(): Int {
       return jobList.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentCard = jobList[position]

        holder.jobTitle.text = currentCard.job_role

        holder.secondTitle.text = currentCard.company_name

        val title = currentCard.title ?: ""
        holder.jobDescription.text = if (title.length > 30) {
            "${title.take(30)}..."
        } else {
            title
        }

      currentCard.creatives.map {
          Picasso.get()
              .load(it.file)
              .placeholder(R.drawable.logo)
              .error(R.drawable.error)
              .into(holder.jobProfile)
      }


        holder.salaryText.text = currentCard.primary_details.Salary

        viewModel.getBookmarkedJobs().observeForever { bookmarkedJobs ->
            val isBookmarked = bookmarkedJobs.any { it.id == currentCard.id }
            if (isBookmarked) {
                holder.bookMark.setImageResource(R.drawable.baseline_bookmark_added_24)

            } else {
                holder.bookMark.setImageResource(R.drawable.baseline_bookmark_add_24)
            }
        }

        holder.bookMark.setOnClickListener {
           onClick.saveJobFromList(currentCard)
            viewModel.getBookmarkedJobs().observeForever { bookmarkedJobs ->
                val isBookmarked = bookmarkedJobs.any { it.id == currentCard.id }
                if (isBookmarked) {
                    holder.bookMark.setImageResource(R.drawable.baseline_bookmark_added_24)
                } else {
                    holder.bookMark.setImageResource(R.drawable.baseline_bookmark_add_24)
                }
            }
        }

        holder.locationText.text = currentCard.primary_details.Place



       val whatsappNo =  currentCard.whatsapp_no
        if(whatsappNo==null){
            holder.contactNoText.text = "Not given"
        }else{
            holder.contactNoText.text = whatsappNo
        }

        holder.experienceText.text = "Expereince:${currentCard.primary_details.Experience}"



        holder.cardBtn.setOnClickListener {
            onClick.onClick(currentCard)

        }
    }



    inner class JobViewHolder(view:View) : RecyclerView.ViewHolder(view){


         val jobTitle : TextView = view.findViewById(R.id.job_title_text)
        val  jobProfile : ImageView = view.findViewById(R.id.jobProfileImg)
         val secondTitle : TextView = view.findViewById(R.id.job_second_title_text)
         val jobDescription : TextView = view.findViewById(R.id.job_desc_text)
         val salaryText : TextView = view.findViewById(R.id.salary_text)
         val bookMark : ImageView = view.findViewById(R.id.bookmark_img)
         val locationText : MaterialButton = view.findViewById(R.id.locationText)
         val contactNoText : MaterialButton = view.findViewById(R.id.contactNo_text)
         val experienceText : MaterialButton = view.findViewById(R.id.experienceText)
        val cardBtn : LinearLayout = view.findViewById(R.id.goTodetailBtn)
    }

//
//    @SuppressLint("NotifyDataSetChanged")
//    fun submitList(newList: List<JobListing>) {
//        val uniqueList = newList.distinctBy { it.id }
//        jobList.clear()
//        jobList.addAll(uniqueList)
//        notifyDataSetChanged()
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<JobListing>) {
        val currentSize = jobList.size
        val uniqueNewItems = newList.distinctBy { it.id }.filter { newItem ->
            jobList.none { it.id == newItem.id }
        }
        jobList.addAll(uniqueNewItems)
        if (currentSize == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(currentSize, uniqueNewItems.size)
        }
    }

}
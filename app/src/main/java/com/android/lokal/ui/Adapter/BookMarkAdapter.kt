package com.android.lokal.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.lokal.R
import com.android.lokal.data.DataClasses.JobListing
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class BookMarkAdapter(var jobList: ArrayList<JobListing>, val context: Context,val onDelete:BookMarkListener) : RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_mark_card,parent,false)
        return BookMarkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    override fun onBindViewHolder(holder: BookMarkViewHolder, position: Int) {
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


        holder.deleteBtn.setOnClickListener {
           onDelete.onDelete(currentCard)
        }

        holder.locationText.text = currentCard.primary_details.Place

        holder.contactNoText.text = currentCard.whatsapp_no
        holder.experienceText.text = "Expereince:${currentCard.primary_details.Experience}"


    }



    inner class BookMarkViewHolder(view: View) : RecyclerView.ViewHolder(view){


        val jobTitle : TextView = view.findViewById(R.id.job_title_text_bookMark)
        val  jobProfile : ImageView = view.findViewById(R.id.jobProfileImg_bookMark)
        val secondTitle : TextView = view.findViewById(R.id.job_second_title_text_bookMark)
        val jobDescription : TextView = view.findViewById(R.id.job_desc_text_bookMark)
        val salaryText : TextView = view.findViewById(R.id.salary_text_bookMark)
        val deleteBtn : ImageView = view.findViewById(R.id.deleteJob_BookMark)
        val locationText : MaterialButton = view.findViewById(R.id.locationText_bookMark)
        val contactNoText : MaterialButton = view.findViewById(R.id.contactNo_text_bookMark)
        val experienceText : MaterialButton = view.findViewById(R.id.experienceText_BookMark)

    }


    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<JobListing>) {
        val uniqueList = newList.distinctBy { it.id }
        jobList.clear()
        jobList.addAll(uniqueList)
        notifyDataSetChanged()
    }


}
package com.android.lokal.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.lokal.R
import com.android.lokal.data.DataClasses.JobListing
import com.android.lokal.data.local.JobDatabase
import com.android.lokal.data.remote.repository.SearchJobRepository
import com.android.lokal.ui.ViewModel.JobViewModel
import com.android.lokal.ui.ViewModel.SearchJobViewModelFactory
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class JobDetailsActivity : AppCompatActivity() {

    private lateinit var profile : ImageView
    private lateinit var bookMarkBtn : ImageView
    private lateinit var jobName_text : TextView
    private lateinit var companyName_text : TextView
    private lateinit var jobCategory : TextView
    private lateinit var jobVacancyDisplayText : MaterialButton
    private lateinit var applicationViewedText : TextView
    private lateinit var jobLocationText : TextView
    private lateinit var jobExpText : TextView
    private lateinit var workTime : TextView
    private lateinit var jobGenderText : TextView
    private lateinit var jobSalary : TextView
    private lateinit var qualification : TextView
    private lateinit var workShiftText : TextView
    private lateinit var jobVacancies : TextView
    private lateinit var descriptionTitle : TextView
    private lateinit var otherDetailText : TextView
    private lateinit var callBtn : MaterialButton
    private lateinit var viewModel: JobViewModel
    private lateinit var whatsAppNo : TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.card_detail)
        declareID()
        val jobListing: JobListing? = intent.getParcelableExtra("job_listing")

        val repository = SearchJobRepository()
        val dao = JobDatabase.getDatabase(this).jobDao()
        val factory = SearchJobViewModelFactory(repository, dao)

        viewModel = ViewModelProvider(this, factory).get(JobViewModel::class.java)

        lifecycleScope.launch {
           val id =  viewModel.getJobById(jobListing!!.id)
            if(id!=null){
                bookMarkBtn.setImageResource(R.drawable.baseline_bookmark_added_24)
            }
        }

        if(jobListing!=null){
        val img = jobListing.creatives.get(0).file
        Picasso.get()
               .load(img)
               .placeholder(R.drawable.logo)
               .error(R.drawable.error)
               .into(profile)


       val workShift = jobListing.contentV3.V3.get(2).field_value
       val gender = jobListing.contentV3.V3.get(1).field_value

           val number = jobListing.whatsapp_no
            if(number!=null){
                whatsAppNo.text = "Whatsapp No: ${number}"
            }else{
                whatsAppNo.text = " Whatsapp No: Not given"
            }

//display UI
       jobName_text.text = jobListing.job_role
       companyName_text.text = jobListing.company_name
       jobCategory.text = jobListing.job_category
       jobVacancyDisplayText.text = "Vacancies: ${jobListing.openings_count}"
       applicationViewedText.text = "${jobListing.views} person viewed"
       jobLocationText.text = jobListing.primary_details.Place
       jobExpText.text = jobListing.primary_details.Experience
       workTime.text = jobListing.job_hours
       jobGenderText.text = gender
       workShiftText.text = workShift
       jobSalary.text = jobListing.primary_details.Salary
       qualification.text = jobListing.primary_details.Qualification
       jobVacancies.text = "Vacancies: ${jobListing.openings_count}"
       descriptionTitle.text  = jobListing.title
       otherDetailText.text = jobListing.other_details


   }

        bookMarkBtn.setOnClickListener {
      if(jobListing!=null){
          lifecycleScope.launch {
              val id = viewModel.getJobById(jobListing.id)
              if(id!=null){
                  Toast.makeText(this@JobDetailsActivity, "Job already saved", Toast.LENGTH_SHORT).show()
              }else{
                  viewModel.saveLocalJob(jobListing)
                  bookMarkBtn.setImageResource(R.drawable.baseline_bookmark_added_24)
              }
          }
      }else{
          Toast.makeText(this ,"Something went wrong",Toast.LENGTH_SHORT).show()
      }
        }

    }






    fun declareID(){
        profile = findViewById(R.id.job_profile_detail)
        bookMarkBtn = findViewById(R.id.job_bookmark_detail)
        jobName_text = findViewById(R.id.job_name_title)
        companyName_text = findViewById(R.id.comapany_name_title)
        jobCategory = findViewById(R.id.jobCategory_text)
        jobVacancyDisplayText = findViewById(R.id.vacany_dsp_text_detail)
        applicationViewedText = findViewById(R.id.applicationViewed_details)
        jobLocationText = findViewById(R.id.jobLocation_detail)
        jobExpText = findViewById(R.id.jobExperience_detail)
        workTime = findViewById(R.id.job_worktime_detail)
        jobGenderText = findViewById(R.id.jobGender_detail)
        jobSalary = findViewById(R.id.jobSalary_detail)
        qualification = findViewById(R.id.jobQualification_detail)
        workShiftText = findViewById(R.id.job_workShift_detail)
        jobVacancies = findViewById(R.id.job_vacancies_detail_txt)
        descriptionTitle = findViewById(R.id.otherDetail_title_txt)
        otherDetailText = findViewById(R.id.otherdetail_desc_text)
        callBtn = findViewById(R.id.call_hr_detail_btn)
        whatsAppNo = findViewById(R.id.whatsappNo_bookMark_text)

    }




}
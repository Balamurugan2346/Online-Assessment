package com.android.lokal.data.DataClasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.parcelize.RawValue

@Entity(tableName = "job_table", indices = [Index(value = ["id"], unique = true)])
@Parcelize
@Serializable
data class JobListing(
    @PrimaryKey val id: Int,
    val title: String,
    val type: Int,
    val primary_details:  PrimaryDetails,
    val fee_details:  FeeDetails,
    val job_tags:  List<JobTag>,
    val job_type: Int,
    val job_category_id: Int,
    val qualification: Int,
    val experience: Int,
    val shift_timing: Int,
    val job_role_id: Int,
    val salary_max: Int?,
    val salary_min: Int?,
    val city_location: Int,
    val locality: Int,
    val premium_till: String? = null,
    val content: String? =null,
    val company_name: String,
    val advertiser: Int,
    val button_text: String,
    val custom_link: String,
    val amount: String,
    val views: Int,
    val shares: Int,
    val fb_shares: Int,
    val is_bookmarked: Boolean,
    val is_applied: Boolean,
    val is_owner: Boolean,
    val updated_on: String,
    val whatsapp_no: String? =null,
    val contact_preference: ContactPreference,
    val created_on: String,
    val is_premium: Boolean,
    val creatives:  List<Creative>,
    val videos:  List<Video>,
    val locations:  List<Location>,
    val tags:  List<Tag>,
    val contentV3:  ContentV3,
    val status: Int,
    val expire_on: String,
    val job_hours: String,
    val openings_count: Int,
    val job_role: String,
    val other_details: String,
    val job_category: String,
    val num_applications: Int,
    val enable_lead_collection: Boolean,
    val is_job_seeker_profile_mandatory: Boolean,
    val translated_content:  TranslatedContent,
    val job_location_slug: String,
    val fees_text: String,
    val question_bank_id: Int?,
    val screening_retry: Int?,
    val should_show_last_contacted: Boolean,
    val fees_charged: Int
) : Parcelable
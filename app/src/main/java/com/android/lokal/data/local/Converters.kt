package com.android.lokal.data.local

import androidx.room.TypeConverter
import com.android.lokal.data.DataClasses.ContactPreference
import com.android.lokal.data.DataClasses.ContentV3
import com.android.lokal.data.DataClasses.Creative
import com.android.lokal.data.DataClasses.FeeDetails
import com.android.lokal.data.DataClasses.JobTag
import com.android.lokal.data.DataClasses.Location
import com.android.lokal.data.DataClasses.PrimaryDetails
import com.android.lokal.data.DataClasses.Tag
import com.android.lokal.data.DataClasses.TranslatedContent
import com.android.lokal.data.DataClasses.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    //type convertors for storing data into room a complex data type
    @TypeConverter
    fun fromPrimaryDetails(primaryDetails: PrimaryDetails?): String? {
        return gson.toJson(primaryDetails)
    }

    @TypeConverter
    fun toPrimaryDetails(primaryDetailsString: String?): PrimaryDetails? {
        return gson.fromJson(primaryDetailsString, object : TypeToken<PrimaryDetails>() {}.type)
    }

    @TypeConverter
    fun fromFeeDetails(feeDetails: FeeDetails?): String? {
        return gson.toJson(feeDetails)
    }

    @TypeConverter
    fun toFeeDetails(FeeDetailsString: String?): FeeDetails? {
        return gson.fromJson(FeeDetailsString, object : TypeToken<FeeDetails>() {}.type)
    }

    @TypeConverter
    fun fromJobTagList(jobTagList: List<JobTag>?): String? {
        return gson.toJson(jobTagList)
    }

    @TypeConverter
    fun toJobTagList(jobTagListString: String?): List<JobTag>? {
        return gson.fromJson(jobTagListString, object : TypeToken<List<JobTag>>() {}.type)
    }

    @TypeConverter
    fun fromContactPreference(contactPreference: ContactPreference?): String? {
        return gson.toJson(contactPreference)
    }

    @TypeConverter
    fun toContactPreference(contactPreferenceString: String?): ContactPreference? {
        return gson.fromJson(contactPreferenceString, object : TypeToken<ContactPreference>() {}.type)
    }

    @TypeConverter
    fun fromCreativeList(creatives: List<Creative>?): String? {
        return gson.toJson(creatives)
    }

    @TypeConverter
    fun toCreativeList(creativesString: String?): List<Creative>? {
        val listType = object : TypeToken<List<Creative>>() {}.type
        return gson.fromJson(creativesString, listType)
    }

    @TypeConverter
    fun fromVideoList(videos: List<Video>?): String? {
        return gson.toJson(videos)
    }

    @TypeConverter
    fun toVideoList(videosString: String?): List<Video>? {
        val listType = object : TypeToken<List<Video>>() {}.type
        return gson.fromJson(videosString, listType)
    }

    @TypeConverter
    fun fromLocationList(locations: List<Location>?): String? {
        return gson.toJson(locations)
    }

    @TypeConverter
    fun toLocationList(locationsString: String?): List<Location>? {
        val listType = object : TypeToken<List<Location>>() {}.type
        return gson.fromJson(locationsString, listType)
    }

    @TypeConverter
    fun fromTagList(tags: List<Tag>?): String? {
        return gson.toJson(tags)
    }

    @TypeConverter
    fun toTagList(tagsString: String?): List<Tag>? {
        val listType = object : TypeToken<List<Tag>>() {}.type
        return gson.fromJson(tagsString, listType)
    }

    @TypeConverter
    fun fromContentV3(content: ContentV3?): String? {
        return gson.toJson(content)
    }

    @TypeConverter
    fun toContentV3(contentString: String?): ContentV3? {
        return gson.fromJson(contentString, ContentV3::class.java)
    }

    @TypeConverter
    fun fromTranslatedContent(content: TranslatedContent?): String? {
        return gson.toJson(content)
    }

    @TypeConverter
    fun toTranslatedContent(contentString: String?): TranslatedContent? {
        return gson.fromJson(contentString, TranslatedContent::class.java)
    }
}

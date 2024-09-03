package com.android.lokal.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {


    private const val BASE_URL = "https://testapi.getlokalapp.com"

    private val client = OkHttpClient.Builder().build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
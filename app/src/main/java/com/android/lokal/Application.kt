package com.android.lokal

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

open class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        val sharedpref = getSharedPreferences("theme_prefs",0)
        val isNightMode = sharedpref.getBoolean("NIGHT_MODE",false)
        if (isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}
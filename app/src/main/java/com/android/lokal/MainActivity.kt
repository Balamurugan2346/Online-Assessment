package com.android.lokal

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.android.lokal.ui.Fragments.BookMarkFragment
import com.android.lokal.ui.Fragments.JobsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch


class MainActivity : AppCompatActivity() {
 private lateinit var switch : MaterialSwitch
    private lateinit var sharedPreferences: SharedPreferences

private lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        declareId()
        bottomNavClickListener()
        if (savedInstanceState == null) {
            replaceFragment(JobsFragment(), JOBS_FRAGMENT)
        } else {
            val currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT)
            val fragment = when (currentFragmentTag) {
                JOBS_FRAGMENT -> JobsFragment()
                BOOKMARK_FRAGMENTS -> BookMarkFragment()
                else -> JobsFragment()
            }

            replaceFragment(fragment, currentFragmentTag!!)
        }
        val sharedpref = getSharedPreferences("theme_prefs",0)
        val isNightMode = sharedpref.getBoolean("NIGHT_MODE",false)
        switch.isChecked = isNightMode

        switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveNightModeState(true)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveNightModeState(false)
            }

        }
    }

    fun declareId(){
        bottomNav = findViewById(R.id.bottom_navigation)
        switch = findViewById(R.id.materialSwitch)
    }

fun bottomNavClickListener(){
    bottomNav.setOnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_jobs -> {
                replaceFragment(JobsFragment(), JOBS_FRAGMENT)
                true
            }
            R.id.nav_bookmarks -> {
                replaceFragment(BookMarkFragment(), BOOKMARK_FRAGMENTS)
                true
            }

            else -> false
        }
    }
}

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .commit()
    }

    companion object {
        private const val CURRENT_FRAGMENT = "current_fragment"
        private const val JOBS_FRAGMENT = "jobs_fragment"
        private const val BOOKMARK_FRAGMENTS = "bookmarks_fragment"
    }
    //save the fragment state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        Log.d("sasasawqwq",currentFragment?.tag.toString())
        currentFragment?.let {
            outState.putString(CURRENT_FRAGMENT, it.tag)
        }
    }

    private fun saveNightModeState(isNightMode: Boolean) {
        sharedPreferences = getSharedPreferences("theme_prefs", 0)
        val editor = sharedPreferences.edit()
        editor.putBoolean("NIGHT_MODE", isNightMode)
        editor.apply()
    }
}


package com.example.myprofile.ui

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myprofile.MyApp


abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateView()
    }


    protected abstract fun onCreateView() // Set layout here

    override fun onPause() {
        MyApp.mMyApp.activityPaused()
        super.onPause()
    }

    override fun onResume() {
        MyApp.mMyApp.activityResumed()
        super.onResume()
    }


}
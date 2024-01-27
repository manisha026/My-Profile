package com.example.myprofile

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), LifecycleObserver{

    private var activityVisible: Boolean = false

    fun activityResumed() {
        activityVisible = true
    }

    fun activityPaused() {
        activityVisible = false
    }

    companion object {
        lateinit var mMyApp: MyApp

        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        mMyApp = this
        appContext = applicationContext

    }

}
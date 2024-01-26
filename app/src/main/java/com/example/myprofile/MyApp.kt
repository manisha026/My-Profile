package com.example.myprofile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.myprofile.utils.Utils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(), LifecycleObserver{

    var activityVisible: Boolean = false

    fun isActivityVisible(): Boolean {
        return activityVisible
    }

    fun activityResumed() {
        activityVisible = true
    }

    fun activityPaused() {
        activityVisible = false
    }

    companion object {
        lateinit var mMyApp: MyApp
        lateinit var mUtils: Utils

        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        mMyApp = this
        appContext = applicationContext

    }

}
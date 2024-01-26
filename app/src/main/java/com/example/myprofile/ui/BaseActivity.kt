package com.example.myprofile.ui

import android.Manifest
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.myprofile.MyApp
import java.io.File
import java.util.concurrent.Executor


@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateView()
    }


    protected abstract fun onCreateView() // Set layout here

    fun setTransparentStatusBar() {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }


    override fun onPause() {
        MyApp.mMyApp.activityPaused()
        super.onPause()
    }

    override fun onResume() {
        MyApp.mMyApp.activityResumed()
        super.onResume()
    }


}
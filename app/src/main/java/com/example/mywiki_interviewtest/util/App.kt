package com.example.mywiki_interviewtest.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    companion object {
        
        
        lateinit var context : App
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
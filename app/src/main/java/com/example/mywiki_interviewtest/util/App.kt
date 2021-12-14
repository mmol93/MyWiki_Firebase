package com.example.mywiki_interviewtest.util

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    companion object {
        // 어느 클래스에서도 context를 쓸 수 있게 설정
        // 보통 Toast 같은거 표시할 때 많이 쓰임
        lateinit var context : App
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
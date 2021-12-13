package com.example.mywiki_interviewtest.Ext

import android.view.View

fun View.setOnSingleClickListener(onClick: (view:View) -> Unit){
    var lastClickedTime = 0L

    setOnClickListener {
        if (lastClickedTime < System.currentTimeMillis() - 700) {
            lastClickedTime = System.currentTimeMillis()
            onClick(this)
        }
    }
}
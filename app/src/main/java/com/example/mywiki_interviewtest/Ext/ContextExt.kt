package com.example.mywiki_interviewtest.Ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mywiki_interviewtest.util.App
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View



fun Context.showToast(msg:String){
    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show()
}

fun Context.hideKeyboard(view: View){
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}
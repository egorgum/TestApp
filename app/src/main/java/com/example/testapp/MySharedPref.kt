package com.example.testapp

import android.content.Context
import android.content.SharedPreferences

private const val URL_KEY = "URL_KEY"
class MySharedPref(context: Context){
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(URL_KEY, Context.MODE_PRIVATE)
    }
    fun getURL(): String{
        return prefs.getString(URL_KEY,"")!!
    }
    fun saveURL(url: String){
        prefs.edit().putString(URL_KEY, url).apply()
    }
}
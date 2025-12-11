package ru.netology.nmedia.postRepository

import android.content.Context
import com.google.gson.Gson

class DraftSharedPref(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("draft_file", Context.MODE_PRIVATE)

    fun savePref(key: String, value: String) {
        sharedPref.edit().putString(key,value).apply()
    }
    fun getPref(key: String, defaultString: String = "") : String {
       return sharedPref.getString(key,defaultString) ?: defaultString
    }

    fun remove(key: String) {
        sharedPref.edit().remove(key).apply()
    }

}
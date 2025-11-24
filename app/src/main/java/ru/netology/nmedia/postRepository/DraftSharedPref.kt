package ru.netology.nmedia.postRepository

import android.content.Context
import com.google.gson.Gson

class DraftSharedPref(private val context: Context) {

    fun saveDraft(content: String) {
        context.openFileOutput(DRAFT_FILE, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(content))
        }
    }


    fun readDraft(): String? {
        val file = context.filesDir.resolve(DRAFT_FILE)
        return (if (file.exists()) {
            context.openFileInput(DRAFT_FILE).bufferedReader().use {
                gson.fromJson(it, String::class.java)
            }
        } else "")
    }

    fun clearDraft() {
        context.deleteFile(DRAFT_FILE)
    }

    companion object {
        const val DRAFT_FILE = "draft.json"
        val gson = Gson()
    }
}
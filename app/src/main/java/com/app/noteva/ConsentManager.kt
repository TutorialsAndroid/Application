package com.app.noteva


import android.content.Context

class ConsentManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun isAccepted(): Boolean {
        return prefs.getBoolean("termsAccepted", false)
    }

    fun setAccepted(value: Boolean) {
        prefs.edit().putBoolean("termsAccepted", value).apply()
    }
}
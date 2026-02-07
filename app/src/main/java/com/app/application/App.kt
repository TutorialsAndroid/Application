package com.app.application

import android.app.Application
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialize Firebase App here
        FirebaseApp.initializeApp(this)
    }
}
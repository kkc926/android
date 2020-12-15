package com.example.myapplication

import android.app.Application

class MyApplicationApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // initialize Amplify when application is starting
        Backend.initialize(applicationContext)
    }
}
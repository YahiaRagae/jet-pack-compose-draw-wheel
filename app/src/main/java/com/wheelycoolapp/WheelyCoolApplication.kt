package com.wheelycoolapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WheelyCoolApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
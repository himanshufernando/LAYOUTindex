package com.project.layoutindex

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class LAYOUTindex : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    override fun onTerminate() {
        super.onTerminate()
    }

}
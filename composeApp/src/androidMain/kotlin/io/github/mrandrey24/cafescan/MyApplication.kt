package io.github.mrandrey24.cafescan

import android.app.Application
import qrgenerator.AppContext

class MyApplication : Application() {
    companion object {
        lateinit var INSTANCE: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppContext.apply { set(applicationContext) }
    }
}
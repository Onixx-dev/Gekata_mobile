package com.example.gekata_mobile

import android.app.Application
import android.content.Context
import com.example.gekata_mobile.Network.Clients.DefaultAppContainer

class TestApplication : Application() {

    lateinit var container : DefaultAppContainer

//    val context: Context = applicationContext

    override fun onCreate() {
        super.onCreate()
        instance = this
        container = DefaultAppContainer()
    }

    companion object {
        lateinit var instance: TestApplication
            private set
    }

}
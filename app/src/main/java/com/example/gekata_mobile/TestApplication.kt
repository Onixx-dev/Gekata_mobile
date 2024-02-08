package com.example.gekata_mobile

import android.app.Application
import com.example.gekata_mobile.Network.Clients.DefaultAppContainer

class TestApplication : Application() {

    lateinit var container : DefaultAppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }

}
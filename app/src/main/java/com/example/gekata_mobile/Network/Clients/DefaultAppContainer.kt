package com.example.gekata_mobile.Network.Clients

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.gekata_mobile.Network.Interfaces.ProjectsAPIServise
import com.example.gekata_mobile.Network.Interfaces.RetrofitContainer
import com.example.gekata_mobile.Network.Repository.Realisation.ProjectsRepository
import com.example.gekata_mobile.TestApplication
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


class DefaultAppContainer : RetrofitContainer {
    private val BASE_URL = "http://192.168.1.108:8082/"

    private var cache: Cache = Cache( TestApplication.instance.cacheDir , 10 * 1024 * 1024)

    var okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            JacksonConverterFactory.create(ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY))
        )
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()


    private val retrofitService: ProjectsAPIServise by lazy {
        retrofit.create(ProjectsAPIServise::class.java)
    }

    override val projectsRepository: ProjectsRepository by lazy {
        ProjectsRepository(retrofitService)
    }

}
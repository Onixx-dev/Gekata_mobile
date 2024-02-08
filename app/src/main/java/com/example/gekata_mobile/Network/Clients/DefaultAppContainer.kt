package com.example.gekata_mobile.Network.Clients

import com.example.gekata_mobile.Network.Interfaces.ProjectsAPIServise
import com.example.gekata_mobile.Network.Interfaces.RetrofitContainer
import com.example.gekata_mobile.Network.Repository.Interfaces.IProjectsRepository
import com.example.gekata_mobile.Network.Repository.Realisation.ProjectsRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class DefaultAppContainer : RetrofitContainer {
    private val BASE_URL = "http://192.168.1.108:8082/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create(ObjectMapper()
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        )
        )
        .baseUrl(BASE_URL)
        .build()



    private val retrofitService: ProjectsAPIServise by lazy {
        retrofit.create(ProjectsAPIServise::class.java)
    }

    override val projectsRepository: ProjectsRepository by lazy {
        ProjectsRepository(retrofitService)
    }

}
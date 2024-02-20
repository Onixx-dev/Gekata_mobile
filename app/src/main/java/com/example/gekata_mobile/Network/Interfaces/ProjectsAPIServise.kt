package com.example.gekata_mobile.Network.Interfaces

import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.TransportModels.BuildingList
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectsAPIServise {




    @GET("BuildingController/getList")
    suspend fun getBuildingsList(
    ): ArrayList<TransportBuilding>

    @GET("BuildingController/getBuildingModel")
    suspend fun getProjectById(
        @Query("id") id: Int
    ): Project

    @GET("BuildingController/check")
    suspend fun checkConnection() : String

}
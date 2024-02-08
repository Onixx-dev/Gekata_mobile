package com.example.gekata_mobile.Network.Repository.Interfaces

import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding

interface IProjectsRepository {

    suspend fun getBuildingsList(): ArrayList<TransportBuilding>

    suspend fun getProjectById(id : Int): Project

    suspend fun checkConnection() : String

}
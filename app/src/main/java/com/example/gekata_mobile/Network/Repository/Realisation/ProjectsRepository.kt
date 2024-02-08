package com.example.gekata_mobile.Network.Repository.Realisation

import android.util.Log
import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.Interfaces.ProjectsAPIServise
import com.example.gekata_mobile.Network.Repository.Interfaces.IProjectsRepository
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding

class ProjectsRepository(
    private val projectsAPIServise: ProjectsAPIServise
) : IProjectsRepository {

    override suspend fun getBuildingsList(): ArrayList<TransportBuilding> {
        return projectsAPIServise.getBuildingsList()
    }

    override suspend fun getProjectById(id: Int): Project {
        val result = projectsAPIServise.getProjectById(id)
        Log.d("api",result.toString())
        return result
    }

    override suspend fun checkConnection(): String {
        val result = projectsAPIServise.checkConnection()
        Log.d("api",result)
        return result
    }

}
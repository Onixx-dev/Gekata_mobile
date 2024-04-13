package com.example.gekata_mobile.ModelView.Interfaces

import com.example.gekata_mobile.ModelView.Realisation.PathFinding.PathContainer
import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.TransportModels.BuildingList
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding
import kotlinx.coroutines.Job

interface ProjectsUIStates {
    data class Success(val resultSearch: ArrayList<TransportBuilding>) : ProjectsUIStates
    data class PathComplete(val pathContainer: PathContainer) : ProjectsUIStates
    data class BuildingPath(val operation: () -> Unit ) : ProjectsUIStates
    object Error : ProjectsUIStates
    object Loading : ProjectsUIStates

}



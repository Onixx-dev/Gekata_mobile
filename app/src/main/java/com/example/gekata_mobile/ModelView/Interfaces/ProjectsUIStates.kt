package com.example.gekata_mobile.ModelView.Interfaces

import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.TransportModels.BuildingList
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding

interface ProjectsUIStates {
    data class Success(val resultSearch: ArrayList<TransportBuilding>) : ProjectsUIStates
    object Error : ProjectsUIStates
    object Loading : ProjectsUIStates
}



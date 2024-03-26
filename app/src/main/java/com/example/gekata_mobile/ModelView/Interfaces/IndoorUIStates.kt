package com.example.gekata_mobile.ModelView.Interfaces

import com.example.gekata_mobile.ModelView.Realisation.PathFinding.PathContainer
import com.example.gekata_mobile.Models.Basic.Level
import com.example.gekata_mobile.Models.Basic.WayPoint
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding

interface IndoorUIStates {

    data class Success(val pathContainer: PathContainer  ) : IndoorUIStates
    data class Error(val message: String) : IndoorUIStates
    object Loading : IndoorUIStates

}
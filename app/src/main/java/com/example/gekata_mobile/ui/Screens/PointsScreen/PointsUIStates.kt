package com.example.gekata_mobile.ui.Screens.PointsScreen
import com.example.gekata_mobile.Models.Basic.Project

interface PointsUIStates {

    data class SuccessStartPoint(val startPoint: Project) : PointsUIStates
    data class SuccessEndPoint(val endPoint: Project) : PointsUIStates
    object Empty : PointsUIStates
    object Error : PointsUIStates
    object Loading : PointsUIStates

}
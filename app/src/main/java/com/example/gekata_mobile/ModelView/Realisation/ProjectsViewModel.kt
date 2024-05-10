package com.example.gekata_mobile.ModelView.Realisation

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gekata_mobile.ModelView.Interfaces.IndoorUIStates

import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.GraphPathFinder
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.LevelPathFinder
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.PathContainer
import com.example.gekata_mobile.Models.Basic.Building
import com.example.gekata_mobile.Models.Basic.InterestPoint
import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Models.Basic.WayPoint
import com.example.gekata_mobile.Network.Repository.Realisation.ProjectsRepository
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding
import com.example.gekata_mobile.TestApplication
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.ZonedDateTime

class ProjectsViewModel(
    private val projectsRepository: ProjectsRepository
) : ViewModel() {


    /////////////////////////////////////
    ////////STATES
    var projectsUIStates: ProjectsUIStates by mutableStateOf(ProjectsUIStates.Loading)


    /////////////////////////////////////
    ////////START LOCATION DATA
    var startBuilding: Project? by mutableStateOf(null)
        private set

    var startPoint: InterestPoint? by mutableStateOf(null)

    lateinit var startWaypoint: WayPoint


    /////////////////////////////////////
    ////////END LOCATION DATA
    var endBuilding: Project? by mutableStateOf(null)
        private set

    var endPoint: InterestPoint? by mutableStateOf(null)

    lateinit var endWaypoint: WayPoint


    /////////////////////////////////////
    ////////CANVAS
    var levelCanvasOffset: Offset by mutableStateOf(Offset(0f, 0f))

    var levelCanvasRotate: Float by mutableFloatStateOf(0f)

    var levelCanvasScale: Float by mutableFloatStateOf(1f)

    /////////////////////////////////////
    ////////Level Selector

    var buildingsList: ArrayList<TransportBuilding> = arrayListOf()

    var levelIndex: Int by mutableIntStateOf(0)

    var buildingIndex: Int = 0

    ////////////////////////////////////////
    //////// YANDEX MAP



    init {
        getProjectsList()
    }

    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////  Indoor View

    fun getBuildingWithIndex(index: Int = buildingIndex): Building {
        return if (index == 0)
            startBuilding!!.building!![0]
        else
            endBuilding!!.building!![0]
    }

    fun getLevelsWay() {

//            projectsUIStates = ProjectsUIStates.Loading
            projectsUIStates =
                try {
                    levelIndex = 0
                    if (startBuilding!!.building!!.first().id!! == endBuilding!!.building!!.first().id!!) {
                        Log.d("puk", "puk")
                        ProjectsUIStates.Error
                        //TODO start & end building are equals
                    } else {
                            startWaypoint = WayPoint()
                            startWaypoint.isEmpty = true
                            endWaypoint = WayPoint()
                            endWaypoint.isEmpty = true
                        val graphPathFinder = GraphPathFinder(startPoint!!,endPoint!!,startWaypoint,endWaypoint)

                        ProjectsUIStates.PathComplete(
                            PathContainer(
                                startBuildingPoints = graphPathFinder.pathIndoor(
                                    startBuilding!!.building!![0],
                                    isPathToExit = true,
                                ),
                                endBuildingPoints = graphPathFinder.pathIndoor(
                                    endBuilding!!.building!![0],
                                    isPathToExit = false
                                ),
                                startPoint!!,
                                endPoint!!
                            )
                        )
                    }
                } catch (e: IOException) {
                    ProjectsUIStates.Error
                }

    }


    suspend fun awaitMilliseconds(x: Long) {
        projectsUIStates = ProjectsUIStates.BuildingPath(operation = {getLevelsWay()})
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////  Projects View
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun getProjectsList() {
        viewModelScope.launch {
            projectsUIStates = ProjectsUIStates.Loading
            projectsUIStates =
                try {
                    buildingsList = projectsRepository.getBuildingsList()
                    ProjectsUIStates.Success(buildingsList)
                } catch (e: IOException) {
                    Log.d("api error", "IO = " + e.message)
                    ProjectsUIStates.Error
                } catch (e: HttpException) {
                    Log.d("api error", "HTTP = " + e.message)
                    ProjectsUIStates.Error
                }
        }
    }

    fun getProjectByIdAsStartPoint(id: Int) {
        viewModelScope.launch {
            try {
                startBuilding = projectsRepository.getProjectById(id)
            } catch (e: IOException) {
                Log.d("api error", "IO = " + e.message)
                ProjectsUIStates.Error
            } catch (e: HttpException) {
                Log.d("api error", "HTTP = " + e.message)
                ProjectsUIStates.Error
            }
        }
    }

    fun getProjectByIdAsEndPoint(id: Int) {
        viewModelScope.launch {
            try {
                endBuilding = projectsRepository.getProjectById(id)
            } catch (e: IOException) {
                Log.d("api error", "IO = " + e.message)
                ProjectsUIStates.Error
            } catch (e: HttpException) {
                Log.d("api error", "HTTP = " + e.message)
                ProjectsUIStates.Error
            }
        }
    }

    fun check() {
        viewModelScope.launch {
            try {
                val res = projectsRepository.checkConnection()
            } catch (e: IOException) {
                ProjectsUIStates.Error
            } catch (e: HttpException) {
                ProjectsUIStates.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TestApplication)
                val projectsRepository = application.container.projectsRepository
                ProjectsViewModel(projectsRepository = projectsRepository)
            }
        }
    }


}
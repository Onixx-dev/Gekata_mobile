package com.example.gekata_mobile.ModelView.Realisation

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates
import com.example.gekata_mobile.Models.Basic.InterestPoint
import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.Repository.Realisation.ProjectsRepository
import com.example.gekata_mobile.TestApplication
import com.example.gekata_mobile.ui.Screens.PointsScreen.PointsUIStates
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.LoadingScreen
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProjectsViewModel(
    private val projectsRepository : ProjectsRepository
) : ViewModel() {

    var projectsUIStates: ProjectsUIStates by mutableStateOf(ProjectsUIStates.Loading)
        private set

    var startBuilding: Project? by mutableStateOf(null)
        private set

    var startPoint: InterestPoint? by mutableStateOf(null)

    var endBuilding: Project? by mutableStateOf(null)
        private set

    var endPoint: InterestPoint? by mutableStateOf(null)


    init {
        Log.d("api","init start")
        getProjectsList()
        Log.d("api","init end")
    }

    fun getProjectsList() {
        viewModelScope.launch {
            projectsUIStates = ProjectsUIStates.Loading
            projectsUIStates =
                try {
                    ProjectsUIStates.Success(projectsRepository.getBuildingsList())
                } catch (e: IOException) {
                    Log.d("api", "IO = " + e.message)
                    ProjectsUIStates.Error
                } catch (e: HttpException) {
                    Log.d("api", "HTTP = " + e.message)
                    ProjectsUIStates.Error
                }
        }
    }


    fun getProjectByIdAsStartPoint(id : Int){
        viewModelScope.launch {
            try {
                startBuilding = projectsRepository.getProjectById(id)
            } catch (e: IOException) {
                Log.d("api", "IO = " + e.message)
                ProjectsUIStates.Error
            } catch (e: HttpException) {
                Log.d("api", "HTTP = " + e.message)
                ProjectsUIStates.Error
            }
        }
    }

    fun getProjectByIdAsEndPoint(id : Int){
        viewModelScope.launch {
            try {
                endBuilding = projectsRepository.getProjectById(id)
            } catch (e: IOException) {
                Log.d("api", "IO = " + e.message)
                ProjectsUIStates.Error
            } catch (e: HttpException) {
                Log.d("api", "HTTP = " + e.message)
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
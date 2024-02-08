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
import com.example.gekata_mobile.Network.Repository.Realisation.ProjectsRepository
import com.example.gekata_mobile.TestApplication
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProjectsViewModel(
    private val projectsRepository : ProjectsRepository
) : ViewModel() {

    var projectsUIStates: ProjectsUIStates by mutableStateOf(ProjectsUIStates.Loading)
        private set


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

    fun getProjectById(id : Int){
        viewModelScope.launch {

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
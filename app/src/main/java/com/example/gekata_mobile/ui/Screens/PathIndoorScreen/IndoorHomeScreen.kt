package com.example.gekata_mobile.ui.Screens.PathIndoorScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gekata_mobile.ModelView.Interfaces.IndoorUIStates
import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.ErrorScreen
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.LoadingScreen

@Composable
fun IndoorHomeScreen(
    projectsViewModel: ProjectsViewModel,
    projectsUIStates: ProjectsUIStates,
    modifier: Modifier = Modifier
) {
    when (projectsUIStates) {
        is ProjectsUIStates.Loading -> LoadingScreen(modifier)
        is ProjectsUIStates.Error -> ErrorScreen({}, modifier)
        is ProjectsUIStates.BuildingPath -> LoadingScreen(modifier)
        is ProjectsUIStates.PathComplete -> IndoorMainScreen(projectsViewModel = projectsViewModel, pathContainer = projectsUIStates.pathContainer)
        is ProjectsUIStates.Success -> LoadingScreen(modifier)

    }
}
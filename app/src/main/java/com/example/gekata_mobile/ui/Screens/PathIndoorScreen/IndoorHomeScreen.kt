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
    indoorUIStates: IndoorUIStates,
    modifier: Modifier = Modifier
) {
    when (indoorUIStates) {
        is IndoorUIStates.Loading -> LoadingScreen(modifier)
        is IndoorUIStates.Success -> IndoorMainScreen(projectsViewModel = projectsViewModel, pathContainer = indoorUIStates.pathContainer)
        is IndoorUIStates.Error -> ErrorScreen({}, modifier)
    }
}
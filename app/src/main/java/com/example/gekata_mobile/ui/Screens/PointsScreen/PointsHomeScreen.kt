package com.example.gekata_mobile.ui.Screens.PointsScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.ErrorScreen
import com.example.gekata_mobile.ui.Screens.ProjectsListScreen.LoadingScreen

@Composable
fun PointsHomeScreen(
    projectsViewModel: ProjectsViewModel,
    projectsUIStates: ProjectsUIStates,
    modifier: Modifier = Modifier
) {
    when (projectsUIStates) {
        is ProjectsUIStates.Loading -> LoadingScreen(modifier)
        is ProjectsUIStates.Error -> ErrorScreen({projectsViewModel.getProjectsList()}, modifier)

        is ProjectsUIStates.Success -> PointsSelectionScreen(projectsViewModel = projectsViewModel,result = projectsUIStates.resultSearch, modifier = modifier)
        is ProjectsUIStates.BuildingPath -> PointsPathBuildingScreen(projectsViewModel = projectsViewModel, operation = projectsUIStates.operation)
        is ProjectsUIStates.PathComplete -> PointsSelectionScreen(projectsViewModel = projectsViewModel, modifier = modifier, result = projectsViewModel.buildingsList)

    }
}
package com.example.gekata_mobile.ui.Screens.ProjectsListScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates

@Composable
fun HomeScreen(
    projectsUIStates: ProjectsUIStates,
    retryAction: () -> Unit,
    modifier: Modifier
) {
    when (projectsUIStates) {
        is ProjectsUIStates.Loading -> LoadingScreen(modifier)
        is ProjectsUIStates.Success -> ProjectsGridScreen(
            buildings = projectsUIStates.resultSearch,
            modifier = modifier
        )
        is ProjectsUIStates.Error -> ErrorScreen(retryAction = retryAction, modifier)
    }
}



package com.example.gekata_mobile.ui.Screens.ProjectsListScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.R
import com.example.gekata_mobile.ui.Screens.Navigation.BottomBarItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsApp(
    modifier: Modifier = Modifier,
) {
    val projectsViewModel: ProjectsViewModel = viewModel(factory = ProjectsViewModel.Factory)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar (
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },

        ) {
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                projectsUIStates = projectsViewModel.projectsUIStates,
                retryAction = { projectsViewModel.getProjectsList() },
                modifier = modifier
            )
        }
    }
}













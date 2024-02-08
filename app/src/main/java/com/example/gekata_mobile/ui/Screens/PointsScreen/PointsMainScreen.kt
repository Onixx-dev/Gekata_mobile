package com.example.gekata_mobile.ui.Screens.PointsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel

@Composable
fun PointsMainScreen(modifier: Modifier = Modifier) {

    val projectsViewModel: ProjectsViewModel = viewModel(factory = ProjectsViewModel.Factory)

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "select your points"
        )
        Button(
            onClick = {
                      projectsViewModel
            },
            modifier = modifier
            ) {

        }
    }
}



@Composable
fun BuildingsListAnchor(modifier: Modifier, text : String) {
    Box {
        Row {
            Text(text = text,)
            Icon(Icons.Default.ArrowDropDown,"")
        }
    }
}


package com.example.gekata_mobile.ui.Screens.PointsScreen

import android.util.Log
import android.widget.Toast
import androidx.collection.emptyLongSet
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gekata_mobile.ModelView.Interfaces.ProjectsUIStates
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.Models.Basic.InterestPoint
import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Models.Basic.WayPoint
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding

@Composable
fun PointsMainScreen(modifier: Modifier = Modifier) {

    val projectsViewModel: ProjectsViewModel = viewModel(factory = ProjectsViewModel.Factory)

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {

            test(
                projectsUIStates = projectsViewModel.projectsUIStates,
                modifier = modifier
            )
        }
    }
}


@Composable
fun test(
    projectsUIStates: ProjectsUIStates,
    modifier: Modifier
) {
    val projectsViewModel: ProjectsViewModel = viewModel(factory = ProjectsViewModel.Factory)

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (projectsUIStates) {
            is ProjectsUIStates.Success -> {

                ExposedBuildingsDropdown(
                    resultSearch = projectsUIStates.resultSearch,
                    onSelectedAction = { id: Int -> projectsViewModel.getProjectByIdAsStartPoint(id) }
                )
                if (projectsViewModel.startBuilding != null)
                    ExposedPointsDropdown(
                        project = projectsViewModel.startBuilding!!,
                        onSelectedAction = { item: InterestPoint -> projectsViewModel.startPoint = item }
                    )
                else {
                    Text(text = "building not selected")
                }

                ExposedBuildingsDropdown(
                    resultSearch = projectsUIStates.resultSearch,
                    onSelectedAction = { id: Int -> projectsViewModel.getProjectByIdAsEndPoint(id) }
                )
                if (projectsViewModel.endBuilding != null)
                    ExposedPointsDropdown(
                        project = projectsViewModel.endBuilding!!,
                        onSelectedAction = {item: InterestPoint -> projectsViewModel.endPoint = item}
                    )
                else {
                    Text(text = "building not selected")
                }

                Button(onClick = {
                    Log.d("result1", projectsViewModel.startBuilding.toString())
                    Log.d("result2", projectsViewModel.startPoint.toString())
                    Log.d("result3", projectsViewModel.endBuilding.toString())
                    Log.d("result4", projectsViewModel.endPoint.toString())
                }) {

                }

            }

            is ProjectsUIStates.Error -> {
                Text(text = "error")
                Button(
                    onClick = {
                    projectsViewModel.getProjectsList()
                }){
                    Text(text = "try again")
                }
            }

            is ProjectsUIStates.Loading -> {
                Text(text = "loading")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedBuildingsDropdown(
    resultSearch: ArrayList<TransportBuilding>,
    onSelectedAction: (Int) -> Unit,
) {
    val names: ArrayList<String> = arrayListOf()
    for (item in resultSearch) {
        item.name?.let { names.add(it) }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                resultSearch.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name!!) },
                        onClick = {
                            onSelectedAction(item.id!!)
                            selectedText = item.name!!
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedPointsDropdown(project: Project, onSelectedAction: (InterestPoint) -> Unit) {

    var names: ArrayList<String> = arrayListOf()
    val points: ArrayList<InterestPoint> = arrayListOf()

    for (level in project.building!![0].levels) {
        for (item in level.interestPoints) {
            item.name?.let { names.add(it) }
            points.add(item)
        }
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText!!,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )


            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                points.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name!!) },
                        onClick = {
                            onSelectedAction(item)
                            selectedText = item.name!!
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}




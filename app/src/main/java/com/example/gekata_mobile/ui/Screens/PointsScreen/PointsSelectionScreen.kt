package com.example.gekata_mobile.ui.Screens.PointsScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.Models.Basic.InterestPoint
import com.example.gekata_mobile.Models.Basic.Project
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

@Composable
fun PointsSelectionScreen(
                          modifier: Modifier,
                          projectsViewModel: ProjectsViewModel,
                          result: ArrayList<TransportBuilding>

) {
    val composableScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExposedBuildingsDropdown(
                    modifier = modifier,
                    resultSearch = result,
                    onSelectedAction = { id: Int -> projectsViewModel.getProjectByIdAsStartPoint(id) }
                )
                if (projectsViewModel.startBuilding != null)
                    ExposedPointsDropdown(
                        modifier = modifier,
                        project = projectsViewModel.startBuilding!!,
                        onSelectedAction = { item: InterestPoint ->
                            projectsViewModel.startPoint = item
                        }
                    )
                else {
                    Text(text = "building not selected")
                }

                ExposedBuildingsDropdown(
                    modifier = modifier,
                    resultSearch = result,
                    onSelectedAction = { id: Int -> projectsViewModel.getProjectByIdAsEndPoint(id) }
                )
                if (projectsViewModel.endBuilding != null)
                    ExposedPointsDropdown(
                        modifier = modifier,
                        project = projectsViewModel.endBuilding!!,
                        onSelectedAction = { item: InterestPoint ->
                            projectsViewModel.endPoint = item
                        }
                    )
                else {
                    Text(text = "building not selected")
                }

                Button(onClick = {
                    composableScope.launch {
//                            projectsViewModel.getLevelsWay()
                            projectsViewModel.awaitMilliseconds(300)
                        }
                }) {
                    Text(text = "Accept")
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedBuildingsDropdown(
    modifier: Modifier,
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
        modifier = modifier
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
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
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
fun ExposedPointsDropdown(modifier: Modifier, project: Project, onSelectedAction: (InterestPoint) -> Unit) {

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
        modifier = modifier.fillMaxWidth()
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
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
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
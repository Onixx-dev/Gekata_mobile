package com.example.gekata_mobile.ui.Screens.PathIndoorScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel

@Composable
fun IndoorMainScreen(
    modifier: Modifier = Modifier
) {

    val projectsViewModel: ProjectsViewModel = viewModel(factory = ProjectsViewModel.Factory)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,

    ) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .border(2.dp, color = Color.Cyan)
        ) {
            drawRect(color = Color.Green, size = size)
        }
        LevelSelector(modifier = modifier, projectsViewModel = projectsViewModel)
    }
}



@Composable
fun LevelSelector(modifier: Modifier, projectsViewModel: ProjectsViewModel) {
    Row(modifier = modifier.height(IntrinsicSize.Min))
    {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            readOnly = true,
            label = { Text(text = "building name") },
            modifier = modifier
                .weight(14f)
                .fillMaxHeight()
        )

        Button(
            onClick = { },
            modifier = modifier
                .weight(3f)
                .fillMaxHeight(),
            shape = RectangleShape
            ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
        }


        Button(
            onClick = { },
            modifier = modifier
                .weight(3f)
                .fillMaxHeight(),
            shape = RectangleShape
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = ""
                )
        }

    }


}
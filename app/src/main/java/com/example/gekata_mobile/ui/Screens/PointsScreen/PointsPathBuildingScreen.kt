package com.example.gekata_mobile.ui.Screens.PointsScreen

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext

@Composable
fun PointsPathBuildingScreen(
    modifier: Modifier = Modifier,
    projectsViewModel: ProjectsViewModel,
    operation: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        PointsSelectionScreen(
            modifier = modifier.blur(10.dp),
            projectsViewModel = projectsViewModel,
            result = projectsViewModel.buildingsList
        )
        LoadingOverView(
            modifier = modifier,
            operation = operation)

    }
}

@Composable
fun LoadingOverView(modifier: Modifier,
                    backgroundColor: Color = MaterialTheme.colorScheme.primary,
                    lineColor: Color = MaterialTheme.colorScheme.inversePrimary,
                    operation: () -> Unit
                    ) {

    val fraction = 0.7f

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = modifier
                .height(5.dp)
                .fillMaxWidth(fraction)
                .background(color = lineColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
        }


        val circles = remember { Animatable(initialValue = 0f) }
        LaunchedEffect(key1 = circles) {

//                val test = newSingleThreadContext("Custom Thread")
            val deferred = async(Dispatchers.Default) {
                operation()
            }


//                val def = async {
            Log.d("effects", "animation ${Thread.currentThread().name}")
            circles.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 4000
                        0.0f at 0 using LinearOutSlowInEasing
                        1.0f at 1500 using LinearOutSlowInEasing
                        0.0f at 3000 using LinearOutSlowInEasing
                        0.0f at 4000 using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
//                }


            deferred.await()

        }


        val circleValues = circles.value
        Row(
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(circleValues * fraction)
                    .height(20.dp)
                    .background(color = backgroundColor, shape = CircleShape)
            )

        }
    }

}
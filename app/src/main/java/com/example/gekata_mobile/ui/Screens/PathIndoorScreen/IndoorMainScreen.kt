package com.example.gekata_mobile.ui.Screens.PathIndoorScreen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.LocalContentAlpha
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.LevelPathFinder
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.PathContainer
import com.example.gekata_mobile.ModelView.Realisation.PathFinding.TreeNode
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.Models.Basic.Level
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayDeque
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun IndoorMainScreen(
    modifier: Modifier = Modifier,
    projectsViewModel: ProjectsViewModel,
    pathContainer: PathContainer
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LevelCanvas(
            modifier = modifier,
            projectsViewModel = projectsViewModel,
            pathContainer = pathContainer
        )
        LevelSelector(
            modifier = modifier,
            projectsViewModel = projectsViewModel,
            pathContainer = pathContainer
        )
    }
}


@Composable
fun LevelCanvas(
    modifier: Modifier,
    projectsViewModel: ProjectsViewModel,
    pathContainer: PathContainer
) {
    val paint = Paint().asFrameworkPaint().apply { this.textSize = 50f }

    val offset = remember { mutableStateOf(projectsViewModel.levelCanvasOffset) }
    val scale = remember { mutableFloatStateOf(projectsViewModel.levelCanvasScale) }
    val rotate = remember { mutableFloatStateOf(projectsViewModel.levelCanvasRotate) }

    val rotateMagnitude = 0.2f
    val zoomMagnitude = 0.4f

    Canvas(
        modifier = modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, color = Color.Cyan)
            .pointerInput(Unit) {
                detectTransformGestures { var1, localOffset, zoom, rotation ->
                    run {
                        scale.floatValue *= zoom
                        rotate.floatValue += rotation
                        offset.value = Offset(
                            x = offset.value.x + localOffset.x,
                            y = offset.value.y + localOffset.y
                        )
                    }
                }
            }

    ) {

        drawIntoCanvas {
            it.nativeCanvas.drawText(
                projectsViewModel.levelCanvasOffset.toString(), 20f, 60f, paint
            )
        }

        withTransform({
            translate(offset.value.x, offset.value.y)
            rotate(rotate.floatValue * rotateMagnitude)
            scale(scale.floatValue * zoomMagnitude, scale.floatValue * zoomMagnitude)
        }) {

            pathContainer.getItem(projectsViewModel.levelIndex).walls.forEach {
                drawLine(
                    start = Offset(x = it.startX!!.toFloat(), y = it.startY!!.toFloat()),
                    end = Offset(x = it.endX!!.toFloat(), y = it.endY!!.toFloat()),
                    strokeWidth = 4f,
                    color = Color.Black
                )
            }

            /////// TREE DRAW
            val stack = ArrayDeque<TreeNode>()
            var treeItem: TreeNode
            if (!pathContainer.getItem(projectsViewModel.levelIndex).isTreeNodeInitialised())
                pathContainer.getItem(projectsViewModel.levelIndex).pathTreeRoot = TreeNode(100, 100)
            stack.push(pathContainer.getItem(projectsViewModel.levelIndex).pathTreeRoot)
            do {
                treeItem = stack.pop()
                for (way in treeItem.childs)
                    drawLine(
                        start = Offset(x = treeItem.x.toFloat(), y = treeItem.y.toFloat()),
                        end = Offset(x = way.x.toFloat(), y = way.y.toFloat()),
                        strokeWidth = 3f,
                        color = Color.Red
                    )
                for (child in treeItem.childs)
                    stack.push(child)
            } while (!stack.isEmpty())

            ////////////////////////// FINAL PATH DRAW

            if (pathContainer.getItem(projectsViewModel.levelIndex).isFinalPathInitialised()) {
                stack.clear()
                stack.push(pathContainer.getItem(projectsViewModel.levelIndex).finalPath)
                do {
                    treeItem = stack.pop()
                    for (way in treeItem.childs)
                        drawLine(
                            start = Offset(x = treeItem.x.toFloat(), y = treeItem.y.toFloat()),
                            end = Offset(x = way.x.toFloat(), y = way.y.toFloat()),
                            strokeWidth = 9f,
                            color = Color.Green
                        )
                    for (child in treeItem.childs)
                        stack.push(child)
                } while (!stack.isEmpty())
            }

            ////////////////////////// FINAL PATH DRAW

            if (pathContainer.getItem(projectsViewModel.levelIndex).isFinalPathInitialised()) {
                stack.clear()
                stack.push(pathContainer.getItem(projectsViewModel.levelIndex).test)
                do {
                    treeItem = stack.pop()
                    for (way in treeItem.childs)
                        drawLine(
                            start = Offset(x = treeItem.x.toFloat(), y = treeItem.y.toFloat()),
                            end = Offset(x = way.x.toFloat(), y = way.y.toFloat()),
                            strokeWidth = 4f,
                            color = Color.Red
                        )
                    for (child in treeItem.childs)
                        stack.push(child)
                } while (!stack.isEmpty())
            }


            pathContainer.getItem(projectsViewModel.levelIndex).wayPoints.forEach {
                drawCircle(
                    center = Offset(x = it.x!!.toFloat(), y = it.y!!.toFloat()),
                    radius = 25f,
                    color = Color.Black
                )
                drawCircle(
                    center = Offset(x = it.x!!.toFloat(), y = it.y!!.toFloat()),
                    radius = 23f,
                    color = Color.Cyan
                )
            }

            pathContainer.getItem(projectsViewModel.levelIndex).interestPoints.forEach {
                drawCircle(
                    center = Offset(x = it.x!!.toFloat(), y = it.y!!.toFloat()),
                    radius = 25f,
                    color = Color.Black
                )
                drawCircle(
                    center = Offset(x = it.x!!.toFloat(), y = it.y!!.toFloat()),
                    radius = 23f,
                    color = Color.Green
                )
            }



                drawCircle(
                    center = Offset(
                        x = pathContainer.getItem(projectsViewModel.levelIndex).startPoint.getX().toFloat(),
                        y = pathContainer.getItem(projectsViewModel.levelIndex).startPoint.getY().toFloat()),
                    radius = 14f,
                    color = Color.Magenta
                )
            drawCircle(
                center = Offset(
                    x = pathContainer.getItem(projectsViewModel.levelIndex).endpoint.getX().toFloat(),
                    y = pathContainer.getItem(projectsViewModel.levelIndex).endpoint.getY().toFloat()),
                radius = 14f,
                color = Color.Blue
            )


        }

    }
}


@Composable
fun LevelSelector(
    modifier: Modifier,
    projectsViewModel: ProjectsViewModel,
    pathContainer: PathContainer
) {
    Row(
        modifier = modifier
            .fillMaxHeight(1.0f)
            .fillMaxWidth()
    )
    {
        OutlinedTextField(
            value = pathContainer.getItem(projectsViewModel.levelIndex).name!!,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = LocalContentColor.current.copy(
                    LocalContentAlpha.current
                )
            ),
            modifier = modifier
                .weight(14f)
                .fillMaxHeight()
        )
        Button(
            onClick = {
                if (projectsViewModel.levelIndex > 0) {
                    projectsViewModel.levelIndex = projectsViewModel.levelIndex - 1
                }
            },
            modifier = modifier
                .weight(3f)
                .fillMaxHeight(),
            shape = RectangleShape
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")
        }


        Button(
            onClick = {
                if (projectsViewModel.levelIndex < pathContainer.getSize() - 1) {
                    projectsViewModel.levelIndex = projectsViewModel.levelIndex + 1
                }
            },
            modifier = modifier
                .weight(3f)
                .fillMaxHeight(),
            shape = RectangleShape
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = modifier.fillMaxSize()
            )
        }

    }


}
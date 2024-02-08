package com.example.gekata_mobile.ui.Screens.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItems(
    val route: String,
    val label: String,
    var icon: ImageVector

) {

    object ProjectListItem : BottomBarItems(
        route = Screen.Projects.route,
        label = "projects",
        icon = Icons.Default.List
    )

    object PointsListItem : BottomBarItems(
        route = Screen.Points.route,
        label = "points",
        icon = Icons.Default.Place
    )

    object IndoorListItem : BottomBarItems(
        route = Screen.Indoor.route,
        label = "indoor",
        icon = Icons.Default.Home
    )

    object OutdoorListItem : BottomBarItems(
        route = Screen.Outdoor.route,
        label = "outdoor",
        icon = Icons.Default.LocationOn
    )

}
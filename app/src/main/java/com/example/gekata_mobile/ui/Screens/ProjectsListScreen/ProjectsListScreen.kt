package com.example.gekata_mobile.ui.Screens.ProjectsListScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.gekata_mobile.Network.TransportModels.TransportBuilding


@Composable
fun ProjectsGridScreen(
    buildings: ArrayList<TransportBuilding>,
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        itemsIndexed(buildings) { _, book ->
            ProjectCard(book, modifier)
        }
    }
}


@Composable
fun ProjectCard(
    buildingItem: TransportBuilding,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
//            .requiredHeight(296.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            item.title?.let {
//                Text(
//                    text = it,
//                    textAlign = TextAlign.Center,
//                    modifier = modifier
//                        .padding(top = 4.dp, bottom = 8.dp)
//                )
//            }
        Text(
            modifier = modifier,
            fontSize = 7.em,
            text = buildingItem.name.toString()
        )

        Text(
            modifier = modifier,
            fontSize = 5.em,
            text = buildingItem.address.toString()
        )
        
        
        }
    }

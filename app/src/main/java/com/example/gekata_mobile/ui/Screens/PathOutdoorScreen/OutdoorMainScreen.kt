package com.example.gekata_mobile.ui.Screens.PathOutdoorScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OutdoorMainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    )
    {
        Text(text = "You outdoor path here",
            modifier = modifier
            )
    }

}
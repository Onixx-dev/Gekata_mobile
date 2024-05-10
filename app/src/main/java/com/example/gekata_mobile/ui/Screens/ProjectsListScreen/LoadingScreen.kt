package com.example.gekata_mobile.ui.Screens.ProjectsListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gekata_mobile.R


@Composable
    fun LoadingScreen(modifier: Modifier = Modifier, text: String){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = stringResource(id = R.string.loading))
            }
    }


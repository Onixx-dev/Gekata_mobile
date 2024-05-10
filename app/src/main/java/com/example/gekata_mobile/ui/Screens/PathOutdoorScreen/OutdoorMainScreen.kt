package com.example.gekata_mobile.ui.Screens.PathOutdoorScreen

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import com.example.gekata_mobile.MainActivity
import com.example.gekata_mobile.MapKitActivity
import com.example.gekata_mobile.ModelView.Realisation.ProjectsViewModel
import com.example.gekata_mobile.R
import com.example.gekata_mobile.TestApplication
import com.yandex.mapkit.map.Map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun OutdoorMainScreen(modifier: Modifier = Modifier, projectsViewModel: ProjectsViewModel) {
    Log.d("native start", "")
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        val intent = Intent(context, MapKitActivity::class.java)
        intent.putExtra("start", projectsViewModel.startBuilding!!.building!!.first().yandexAddress)
        intent.putExtra("end", projectsViewModel.endBuilding!!.building!!.first().yandexAddress)
        context.startActivity(intent)

    }

}


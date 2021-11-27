package com.example.wheeloffortune

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wheeloffortune.ui.theme.WheelOfFortuneTheme
import kotlinx.coroutines.android.awaitFrame
import java.util.*



@Composable
fun HomeScreen(navController: NavController, context : Context) {
    Greeting(context)
}

@Composable
private fun HomeScreenContent(){
    Surface(
        color = MaterialTheme.colors.background,
        //modifier = Modifier.fillMaxSize()
    ){

    }
}


@Composable
fun Greeting(context: Context) {
    Column(modifier = Modifier.width(500.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Velkommen til Lykkethjulet!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Image(
            painterResource(R.drawable.lykkehjul),
            contentDescription = "Lykkehjul",
            Modifier.padding(0.dp,0.dp,0.dp,20.dp)
        )

        playButton(context = context)
    }
}

@Composable
fun playButton(context: Context){
    var visible by remember { mutableStateOf(true) }
    if(visible) {
        Button(
            onClick = {
                Toast.makeText(context, "Finder dit ord", Toast.LENGTH_LONG).show()
                visible = false
                Toast.makeText(context, "Ord fundet", Toast.LENGTH_LONG).show()
            },
            enabled = true,
        ) {
            Text(text = "Start Spil")
        }
    } else {

        Text(text = "_ _ _ _ _ _ _", fontSize = 20.sp)
    }
}

@Composable
fun game(){

}


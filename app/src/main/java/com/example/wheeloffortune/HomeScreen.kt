package com.example.wheeloffortune

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


@Composable
fun HomeScreen(navController: NavController) {
    Box() {
        Image(
            painterResource(id = R.drawable.rainbow),
            contentDescription = "baggrund",
            modifier = Modifier
                .fillMaxHeight()
                .scale(3f, 4f)
        )
        LazyColumn(
            modifier = Modifier.width(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Velkommen til Lykkehjulet!",
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            item {
                var currentRotation by rememberSaveable() { mutableStateOf(0f) }
                lykkehjul(rotation = currentRotation)
                currentRotation += 0.5f
                if (currentRotation >= 5000f) currentRotation = 0f
            }
            item {
                Button(
                    onClick = { navController.navigate("PlayScreen") }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(text = "Start spillet", fontSize = 35.sp)
                }
            }
            item {
                Button(
                    onClick = { navController.navigate("SavedScores") }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)

                ) {
                    Text(text = "Hall of Fame", fontSize = 30.sp)
                }

            }

        }
    }
}
@Composable
private fun lykkehjul(rotation : Float){
    Image(
        painterResource(R.drawable.arrow),
        contentDescription = "pil",
        modifier = Modifier
            .size(40.dp)
            .rotate(180f)
            .absoluteOffset(0.dp, -(20).dp)

    )
    Image(
        painterResource(R.drawable.lykkehjullet),
        contentDescription = "Lykkehjul",
        modifier = Modifier
            .padding(0.dp, 15.dp, 0.dp, 0.dp)
            .size(300.dp)
            .rotate(rotation)
    )
}

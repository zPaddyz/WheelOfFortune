package com.example.wheeloffortune

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun SavedScores(navController: NavController) {
    Box() {
        Image(
            painterResource(id = R.drawable.rainbow),
            contentDescription = "baggrund",
            modifier = Modifier
                .fillMaxHeight()
                .scale(3f, 4f)
        )
    }
        LazyColumn() {
            item {

                Text(
                    "Hall Of Fame!",
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            item {
                Text(text = "Herunder kan du se tidligere gemte scores", fontSize = 20.sp)
            }
            item {
                Button(
                    onClick = { navController.navigate("HomeScreen") }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(text = "Tilbage til forside", fontSize = 30.sp)
                }

            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp, 20.dp, 40.dp, 20.dp)
                        .border(25.dp, Color(100, 0, 200), RoundedCornerShape(200.dp))
                        .scale(.8f)
                        .absoluteOffset(0.dp, 10.dp)
                ) {

                        highScores(navController = navController)

                }

            }
        }

}

@Composable
private fun highScores(navController: NavController) {
    val path = navController.context.filesDir
    if(File("$path/test2.txt").canRead()){
        val reader = BufferedReader(FileReader("$path/test2.txt"))
        return Text(text = reader.readText(), fontSize = 30.sp, textAlign = TextAlign.Center)
    }
}
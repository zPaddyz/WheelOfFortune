package com.example.wheeloffortune

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.BufferedReader
import java.io.FileReader


@Composable
fun winningScreen(navController: NavController) {

    Text("Du har vundet eller tabt, TILLYKKE!",fontFamily = FontFamily.Serif,fontSize = 30.sp,textAlign = TextAlign.Center, modifier = Modifier
        .fillMaxWidth()
    )
    Button(onClick = { navController.navigate("HomeScreen") }, modifier = Modifier
        .fillMaxWidth()
        .padding(100.dp)
    ){
        Text(text = "Spil spillet", fontSize = 30.sp) }
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
        .fillMaxWidth()
        .padding(50.dp,180.dp,50.dp,20.dp)
        .border(100.dp, Color(100, 0, 200), RoundedCornerShape(200.dp))
        .scale(.8f)
        ){
        item{
        highScores(navController = navController)
        }
    }
}

@Composable
fun highScores(navController: NavController) {
    val path = navController.context.filesDir
    val reader = BufferedReader(FileReader("$path/test2.txt"))
    return Text(text = reader.readText(), fontSize = 30.sp, textAlign = TextAlign.Center)
}
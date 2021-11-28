package com.example.wheeloffortune

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



@Composable
fun winningScreen(navController: NavController) {

    Text("Du har vundet eller tabt, TILLYKKE!",fontFamily = FontFamily.Serif,fontSize = 30.sp,textAlign = TextAlign.Center, modifier = Modifier
    .size(500.dp))
    Button(onClick = { navController.navigate("HomeScreen") }, modifier = Modifier.size(500.dp).padding(100.dp)) {
        Text(text = "Spil spillet", fontSize = 30.sp) }
}
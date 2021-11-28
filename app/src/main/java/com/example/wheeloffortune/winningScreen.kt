package com.example.wheeloffortune

import android.content.Context
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun winningScreen(navController: NavController) {
    Text("Du har vundet, tillykke!")
    Button(onClick = { navController.navigate("HomeScreen") },) {
        Text(text = "Spil spillet")
    }
}
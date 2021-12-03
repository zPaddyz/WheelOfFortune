package com.example.wheeloffortune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wheeloffortune.ui.theme.WheelOfFortuneTheme


class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WheelOfFortuneTheme {
                val navController = rememberNavController()
                NavHost(navController, "HomeScreen") {
                    composable("PlayScreen") {
                        PlayScreen(navController, this@MainActivity)
                    }

                    composable("HomeScreen") {
                        HomeScreen(navController)
                    }
                    composable("SavedScores") {
                        SavedScores(navController)
                    }
                }
            }
        }
    }
}



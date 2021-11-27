package com.example.wheeloffortune

import android.app.Activity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wheeloffortune.ui.theme.WheelOfFortuneTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WheelOfFortuneTheme {
                val navController = rememberNavController()

                NavHost(navController, "HomeScreen") {
                    composable("HomeScreen") {
                        HomeScreen(navController, this@MainActivity)
                    }
                    /*composable("RecipeScreen") {
                        RecipeScreen(navController)
                    }*/
                }
                // A surface container using the 'background' color from the theme
                /*Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }*/
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hej "+name+" Velkommen til Lykkethjulet!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WheelOfFortuneTheme {
        Greeting("Android")
    }
}*/

package com.example.wheeloffortune

import android.content.Context
import android.view.animation.Interpolator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.math.MathUtils
import androidx.navigation.NavController
import com.google.android.material.animation.AnimationUtils.lerp
import com.google.android.material.math.MathUtils.*
import kotlin.text.StringBuilder

var allPoints = 0

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(navController: NavController, context : Context) {
    Greeting(context, navController)
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
fun lykkehjul(rotation : Float){
    Image(
        //modifier = Modifier.rotateBy(currentAngle, angle), //Custom Modifier
        // rest of the code for image
        painterResource(R.drawable.arrow),
        contentDescription = "pil",
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .size(40.dp)
            .rotate(180f)
    )
    Image(
        //modifier = Modifier.rotateBy(currentAngle, angle), //Custom Modifier
        // rest of the code for image
        painterResource(R.drawable.lykkehjullet),
        contentDescription = "Lykkehjul",
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .size(250.dp)
            .rotate(rotation)
    )
}

@ExperimentalComposeUiApi
@Composable
fun Greeting(context: Context, navController: NavController) {
    Column(modifier = Modifier.width(500.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Velkommen til Lykkethjulet!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        playButton(context = context, navController)
    }

}

var currentRora = 0f


@ExperimentalComposeUiApi
@Composable
fun playButton(context: Context, navController: NavController){
    var currentRotation by rememberSaveable() {mutableStateOf(0f)}
    //lykkehjul(currentRora)



    var visible by remember { mutableStateOf(true) }
    if(visible) {
        currentRotation += 0.5f
        currentRora = currentRotation
        if (currentRotation >= 5000f) currentRotation = 0f
        Button(
            onClick = {
                //Toast.makeText(context, "Finder dit ord", Toast.LENGTH_LONG).show()
                //println("Grader før første roll " + currentRora)
                //currentRora = (0..360).random().toFloat()
                //println("Grader efter første roll " + currentRora)

                visible = false
                //Toast.makeText(context, "Ord fundet", Toast.LENGTH_LONG).show()
            },
            enabled = true,
        ) {
            Text(text = "Start Spil")
        }
    } else {
        game(navController)
    }
}

@Composable
fun spin(){
    currentRora = (0..360).random().toFloat()
}

@ExperimentalComposeUiApi
@Composable
fun game(navController: NavController): String {
    var currentRotation by rememberSaveable() {mutableStateOf(0f)}
    var svar by rememberSaveable { mutableStateOf("") }
    val svarFelt: StringBuilder = remember{ StringBuilder() }
    val randomX by rememberSaveable {mutableStateOf((7..7).random())}
    var bogstav by rememberSaveable { mutableStateOf("")}
    var vandt by rememberSaveable { mutableStateOf(false)}
    var tabte by rememberSaveable { mutableStateOf(false)}
    var antalLiv by rememberSaveable() {mutableStateOf(5)}
    val keyboardController = LocalSoftwareKeyboardController.current
    var korrekteBogstaver by rememberSaveable() {mutableStateOf(0)}
    var rollet by rememberSaveable { mutableStateOf(false)}

    lykkehjul(currentRotation)

    Row() {
        for (i in 1..antalLiv) {
            Image(
                painterResource(R.drawable.heart2),
                contentDescription = "Liv",
                modifier = Modifier
                    .size(50.dp)
                    .border(2.dp, Color.Gray)
            )
        }
    }
    Text(text = "Din kategori er: ", fontSize = 20.sp)

    when(randomX){
        0 -> {
            Text(text = "Et Navn\n", fontSize = 20.sp)
            svar = "Sophie"
        }
        1 -> {
            Text(text = "Et Navn \n", fontSize = 20.sp)
            svar = "Trine"
        }
        2 -> {
            Text(text = "Et Navn\n", fontSize = 20.sp)
            svar = "Jørgen"
        }
        3 -> {
            Text(text = "Et Bilmærke \n", fontSize = 20.sp)
            svar = "Volkswagen"
        }
        4 -> {
            Text(text = "Et Bilmærke \n", fontSize = 20.sp)
            svar = "Ford"
        }
        5 -> {
            Text(text = "Et Bilmærke \n", fontSize = 20.sp)
            svar = "Porche"
        }
        6 -> {
            Text(text = "En Grønsag \n", fontSize = 20.sp)
            svar = "Tomat"
        }
        7 -> {
            Text(text = "En Grønsag \n", fontSize = 20.sp)
            svar = "Selleri"
        }
    }

    if(svarFelt.isEmpty()) {
        for (i in svar) {
            svarFelt.insert(0,"_")
        }
    }
    Text(text = svarFelt.toString(),
        fontSize = 30.sp,
        letterSpacing = 5.sp)

    if(rollet) {
        TextField(
            enabled = true,
            value = bogstav,
            onValueChange = {

                if (bogstav.isEmpty()) {
                    korrekteBogstaver = 0
                    bogstav = it
                    var ingenPasser = true
                    if (bogstav.isEmpty() || bogstav[0].isDigit()) bogstav = " "
                    for (i in svar.indices) {
                        if (bogstav[0] == svar[i]) {
                            svarFelt.replace(i, i + 1, bogstav[0].toString())
                            println(svarFelt)
                            ingenPasser = false
                            korrekteBogstaver++
                            println("antal korrekte bogstaver:" + korrekteBogstaver)
                        }
                    }
                    if (ingenPasser && bogstav != " ") antalLiv -= 1
                    if (antalLiv == 0) tabte = true
                    if (svarFelt.toString() == (svar)) {
                        vandt = true
                    }

                    keyboardController?.hide()
                    rollet = false

                }

            },
            label = { Text("Gæt et bogstav") },
            singleLine = true
        )
    } else {
        Button(onClick = {
            currentRotation = (0..360).random().toFloat()
            println(currentRotation)
            bogstav = ""
            rollet = true
        }) {
            Text(text = "Tryk her at spinne lykkehjullet!")
        }
    }
    if(!rollet){
        when {
            pointSystem(rotation = currentRotation) > 2 -> {
                allPoints += (korrekteBogstaver * pointSystem(currentRotation))
            }
            pointSystem(rotation = currentRotation)==0 -> {
                allPoints = 0
            }
            pointSystem(rotation = currentRotation)==1 -> {
                antalLiv ++
            }
            pointSystem(rotation = currentRotation)==2 -> {
                antalLiv --
            }
        }
        println(korrekteBogstaver.toString() + "Rigtige, Du har nu: " + allPoints + " point")
    }


    if(vandt){
        allPoints = 0
        navController.popBackStack("winningScreen",false)
    }
    if(tabte){
        allPoints = 0
        navController.popBackStack("winningScreen",false)
    }
    Text(text = "Du har: "+allPoints+ " point!",fontSize = 20.sp)
    return svar
}

@Composable
private fun pointSystem(rotation : Float): Int {
    var points = 0

    when {
        rotation > 0 && rotation < 16.4f -> {
            points = 0
        }
        rotation > 16.4 && rotation < 32.8f -> {
            points = 500
        }
        rotation > 32.8f && rotation < 49.2f -> {
            points = 800
        }
        rotation > 49.2f && rotation < 65.6f -> {
            points = 500
        }
        rotation > 65.6f && rotation < 82f -> {
            points = 600
        }
        rotation > 82f && rotation < 98.4f -> {
            points = 1
            //ekstra tur
        }
        rotation > 98.4f && rotation < 114.8f -> {
            points = 500
        }
        rotation > 114.8f && rotation < 131.2f -> {
            points = 1000
        }
        rotation > 131.2f && rotation < 147.6 -> {
            points = 800
        }
        rotation > 147.6 && rotation < 164f -> {
            points = 300
        }
        rotation > 164 && rotation < 180.4f -> {
            points = 100
        }
        rotation > 180.4 && rotation < 196.8f -> {
            points = 1000
        }
        rotation > 196.8 && rotation < 213.2 -> {
            points = 800
        }
        rotation > 213.2 && rotation < 229.6f -> {
            points = 500
        }
        rotation > 229.6 && rotation < 246f -> {
            points = 800
        }
        rotation > 246 && rotation < 262.4f -> {
            points = 2
            // Joker
        }
        rotation > 262.4f && rotation < 278.8f -> {
            points = 500
        }
        rotation > 278.8f && rotation < 295.2f -> {
            points = 600
        }
        rotation > 295.2f && rotation < 311.6f -> {
            points = 500
        }
        rotation > 311.6f && rotation < 328f -> {
            points = 100
        }
        rotation > 328 && rotation < 344f -> {
            points = 800
        }
        rotation > 344 && rotation < 360 -> {
            points = 1500
        }
    }
    println("Du står på: " + points + " Points")
    return points
}
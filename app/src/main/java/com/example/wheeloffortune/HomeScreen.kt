package com.example.wheeloffortune

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

var allPoints = 0

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(navController: NavController, context : Context) {
    Box(){
        Image(
            painterResource(R.drawable.rainbow),
            contentDescription = "baggrund",
            modifier = Modifier
                .fillMaxHeight()
                .scale(3f, 4f)
        )
        Greeting(context, navController)

    }
}

@Composable
fun lykkehjul(rotation : Float){

    Image(
        painterResource(R.drawable.arrow),
        contentDescription = "pil",
        modifier = Modifier
            .size(40.dp)
            .rotate(180f)
            .absoluteOffset(0.dp, -(40).dp)
    )
    Image(
        painterResource(R.drawable.lykkehjullet),
        contentDescription = "Lykkehjul",
        modifier = Modifier
            .padding(0.dp, 35.dp, 0.dp, 0.dp)
            .size(300.dp)
            .rotate(rotation)

    )
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Greeting(context: Context, navController: NavController) {
    Column {
        playButton(context = context, navController)
    }

}

var currentRora = 0f

@ExperimentalAnimationApi
@Composable
fun velkommenTekst(synlig:Boolean){

    val density = LocalDensity.current
    AnimatedVisibility(
        visible = synlig,
        enter = slideInVertically(
            // Slide in from 40 dp from the top.
            initialOffsetY = { with(density) { -40.dp.roundToPx() } }
        ) + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ){

        Text(
            text = "Velkommen til Lykkethjulet!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun playButton(context: Context, navController: NavController){
    var currentRotation by rememberSaveable() {mutableStateOf(0f)}

    var synlig by remember { mutableStateOf(false) }
    //velkommenTekst(synlig)

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
                synlig = true
                visible = false
                //Toast.makeText(context, "Ord fundet", Toast.LENGTH_LONG).show()
            },
            enabled = true,
        ) {
            Text(text = "Start Spil")
        }
    } else {
        game(navController,context)
    }
}

@ExperimentalComposeUiApi
@Composable
fun game(navController: NavController, context: Context): String {
    var currentRotation by rememberSaveable() {mutableStateOf(0f)}
    var svar by rememberSaveable { mutableStateOf("") }
    val svarFelt: StringBuilder = remember{ StringBuilder() }
    val randomX by rememberSaveable {mutableStateOf((0..7).random())}
    var bogstav by rememberSaveable { mutableStateOf("")}
    var vandt by rememberSaveable { mutableStateOf(false)}
    var tabte by rememberSaveable { mutableStateOf(false)}
    var antalLiv by rememberSaveable() {mutableStateOf(5)}
    val keyboardController = LocalSoftwareKeyboardController.current
    var korrekteBogstaver by rememberSaveable() {mutableStateOf(0)}
    var rollet by rememberSaveable { mutableStateOf(1)}
    var kategori by rememberSaveable { mutableStateOf("")}
    Column() {
        Box(){
            Row{
                for (i in 1..antalLiv) {
                    Image(
                        painterResource(R.drawable.heart2),
                        contentDescription = "Liv",
                        modifier = Modifier
                            .size(50.dp)
                            .graphicsLayer(translationX = 10f)
                    )
                }
            }
            Column(modifier = Modifier.width(500.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Din kategori er: \n $kategori", fontSize = 20.sp, modifier = Modifier
                    .absoluteOffset(0.dp,50.dp))
                when(randomX){
                    0 -> {
                        //Text(text = "Et Navn\n", fontSize = 20.sp)
                        kategori = "Et Navn"
                        svar = "Sophie"
                    }
                    1 -> {
                        //Text(text = "Et Navn \n", fontSize = 20.sp)
                        kategori = "Et Navn"
                        svar = "Trine"
                    }
                    2 -> {
                        //Text(text = "Et Navn\n", fontSize = 20.sp)
                        kategori = "Et Navn"
                        svar = "Jørgen"
                    }
                    3 -> {
                        //Text(text = "Et Bilmærke \n", fontSize = 20.sp)
                        kategori = "Et Bilmærke"
                        svar = "Volkswagen"
                    }
                    4 -> {
                        //Text(text = "Et Bilmærke \n", fontSize = 20.sp)
                        kategori = "Et Bilmærke"
                        svar = "Ford"
                    }
                    5 -> {
                        //Text(text = "Et Bilmærke \n", fontSize = 20.sp)
                        kategori = "Et Bilmærke"
                        svar = "Porche"
                    }
                    6 -> {
                        //Text(text = "En Grøntsag \n", fontSize = 20.sp)
                        kategori = "En Grøntsag"
                        svar = "Tomat"
                    }
                    7 -> {
                        //Text(text = "En Grøntsag \n", fontSize = 20.sp)
                        kategori = "En Grøntsag"
                        svar = "Selleri"
                    }
                }
                lykkehjul(rotation = currentRotation)

                if(svarFelt.isEmpty()) {
                    for (i in svar) {
                        svarFelt.insert(0,"_")
                    }
                }
                Box() {
                    Row() {
                        if(svarFelt.isNotEmpty()){
                            if(svar.length-1 < 8){
                                for (i in 0..svar.length-1) {
                                    Text(text = ""+svarFelt[i], textAlign = TextAlign.Center, fontSize = 45.sp,modifier = Modifier
                                        .border(3.dp, Color.LightGray)
                                        .size(55.dp))
                                }
                            } else {
                                for (i in 0..svar.length-1) {
                                    Text(text = ""+svarFelt[i], textAlign = TextAlign.Center, fontSize = 40.sp,modifier = Modifier
                                        .border(3.dp, Color.LightGray)
                                        .size(42.dp)
                                        .absoluteOffset(0.dp, (-8).dp)
                                    )

                                }
                            }
                        }

                    }

                }

                if(rollet == 0) {
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
                                if (antalLiv <= 0) tabte = true
                                if (svarFelt.toString() == (svar)) {
                                    vandt = true
                                    rollet = 4
                                }

                                keyboardController?.hide()
                                if(rollet != 4) rollet = 1

                            }

                        },
                        label = { Text("Gæt et bogstav") },
                        singleLine = true,
                        modifier = Modifier.absoluteOffset(0.dp,10.dp)
                    )
                } else if(rollet == 1 || rollet == 3){
                    Button(onClick = {
                        currentRotation = (0..360).random().toFloat()
                        //currentRotation = 254f
                        println(currentRotation)
                        bogstav = ""
                        rollet = 0
                    }, modifier = Modifier.absoluteOffset(0.dp,10.dp)) {
                        Text(text = "Tryk her at spinne lykkehjullet!")
                    }
                }

                if(rollet == 1){
                    when (pointSystem(rotation = currentRotation)){
                        0 -> {
                            if (allPoints!=0) Toast.makeText(context, "Du ramte FALLIT og mister alle dine point!", Toast.LENGTH_SHORT).show()
                            allPoints = 0
                        }
                        1 -> {
                            antalLiv ++
                            Toast.makeText(context, "Du ramte Ekstra Tur og modtager et ekstra liv!", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {
                            antalLiv --
                            Toast.makeText(context, "Du ramte Jokeren og mister et liv!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            allPoints += (korrekteBogstaver * pointSystem(currentRotation))
                            Toast.makeText(context, "Du havde $korrekteBogstaver. rigtige bogstaver og får: " + (korrekteBogstaver * pointSystem(currentRotation)) + " point!" , Toast.LENGTH_SHORT).show()
                        }
                    }
                    rollet = 3
                    //println("$korrekteBogstaver Rigtige, Du har nu: $allPoints point")
                }

                Text(text = "Point: $allPoints!",fontSize = 35.sp)
                if(vandt || tabte){
                    val path = context.filesDir
                    var navnInput by rememberSaveable { mutableStateOf("") }

                    //midlertidig reset til text fil
                    //BufferedWriter(FileWriter("$path/test2.txt",false)).use {it.write("") }

                    TextField(value = navnInput, singleLine = true,label = { Text(text = "Indtast dit navn og gem dit score")},onValueChange = {
                        navnInput = it
                    }, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {

                                println((BufferedReader(FileReader("$path/test2.txt")).readLines()).size.toString()+" Så stor er den")
                                /*for (string in 0..BufferedReader(FileReader("$path/test2.txt")).readLines().size-1){
                                    val result = BufferedReader(FileReader("$path/test2.txt")).readLines().get(string)
                                    val s2 = result.replace("[^0-9]".toRegex(), "")
                                    val s3 = s2.toInt()
                                    println(s3)
                                    if(allPoints > s3) break

                                }*/
                                val textfil = BufferedReader(FileReader("$path/test2.txt")).readText()

                                //BufferedWriter(FileWriter("$path/test2.txt",true)).use {it.write("$allPoints - $navnInput\n")}


                                BufferedWriter(FileWriter("$path/test2.txt",true)).use {it.write("$allPoints - $navnInput\n")}

                                allPoints = 0
                                vandt = false
                                tabte = false

                                navController.popBackStack("winningScreen",false)}
                        ))
                }
            }
        }



    }
    return svar
}


@Composable
private fun pointSystem(rotation : Float): Int {
    var points = 0

    when {
        rotation >= 0 && rotation < 16.4f -> {
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
    println("Du står på plads: " + points + "")
    return points
}
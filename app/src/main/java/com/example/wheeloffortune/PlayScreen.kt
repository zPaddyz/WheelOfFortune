package com.example.wheeloffortune

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import java.io.*

var allPoints = 0

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun PlayScreen(navController: NavController, context : Context) {
    Box(){
        Image(
            painterResource(id = R.drawable.rainbow),
            contentDescription = "baggrund",
            modifier = Modifier
                .fillMaxHeight()
                .scale(3f, 4f)
        )
        //File(context.filesDir.toString()+"/test2.txt").delete()
        game(navController,context)

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

@ExperimentalComposeUiApi
@Composable
fun game(navController: NavController, context: Context): String {
    //TODO Variabler skal optimeres
    var currentRotation by remember {mutableStateOf(0f)}
    var svar by remember { mutableStateOf("") }
    val svarFelt: StringBuilder = remember{ StringBuilder() }
    val randomX by remember {mutableStateOf((0..12).random())}
    var bogstav by remember { mutableStateOf("")}
    var vandt by remember{ mutableStateOf(false)}
    var tabte by remember { mutableStateOf(false)}
    var antalLiv by remember {mutableStateOf(5)}
    val keyboardController = LocalSoftwareKeyboardController.current
    var korrekteBogstaver by remember {mutableStateOf(0)}
    var rollet by remember { mutableStateOf(1)}
    var kategori by remember { mutableStateOf("")}


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
            //Recyclerview/lazy for at mindre telefoner kan tilgå hele skærmen
            LazyColumn(modifier = Modifier.width(500.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    Text(text = "Din kategori er: \n $kategori", fontSize = 25.sp, modifier = Modifier
                        .absoluteOffset(0.dp,50.dp))
                }
                // Temp løsning - Kategori og svar generator.
                when(randomX){
                    0 -> {
                        kategori = "Et Navn"
                        svar = "Kasper"
                    }
                    1 -> {
                        kategori = "Et Navn"
                        svar = "Trine"
                    }
                    2 -> {
                        kategori = "Et Navn"
                        svar = "Isaac"
                    }
                    3 -> {
                        kategori = "Et Bilmærke"
                        svar = "Volkswagen"
                    }
                    4 -> {
                        kategori = "Et Bilmærke"
                        svar = "Mercedes"
                    }
                    5 -> {
                        kategori = "Et Bilmærke"
                        svar = "Porche"
                    }
                    6 -> {
                        kategori = "En Grøntsag"
                        svar = "Gulerod"
                    }
                    7 -> {
                        kategori = "En Grøntsag"
                        svar = "Selleri"
                    }
                    8 -> {
                        kategori = "En Krydderurt"
                        svar = "Basilikum"
                    }
                    9 -> {
                        kategori = "En kendt "
                        svar = "Estragon"
                    }
                    10 -> {
                        kategori = "Et Navn"
                        svar = "Elizabeth"
                    }
                    11 -> {
                        kategori = "Et Programmeringssprog"
                        svar = "Python"
                    }
                    12 -> {
                        kategori = "Et Programmeringssprog"
                        svar = "JavaScript"
                    }
                }
                item {
                    lykkehjul(rotation = currentRotation)
                }

                if(svarFelt.isEmpty()) {
                    for (i in svar) {
                        svarFelt.insert(0,"_")
                    }
                }
                item {
                    Box() {
                        LazyRow() {
                            item {
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

                    }



                    if(rollet == 0 && currentRotation > 16.4 && currentRotation < 82f || rollet == 0 && currentRotation > 98.4f && currentRotation < 246 ||
                        rollet == 0 && currentRotation > 262.4f) {
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
                                        if (bogstav[0] == svar[i].lowercaseChar() && bogstav[0] != svarFelt[i]|| bogstav[0] == svar[i].uppercaseChar()&& bogstav[0] != svarFelt[i]) {
                                            svarFelt.replace(i, i + 1, svar[i].toString())
                                            println(svarFelt)
                                            ingenPasser = false
                                            korrekteBogstaver++
                                            println("antal korrekte bogstaver:" + korrekteBogstaver)
                                        }
                                    }
                                    if (ingenPasser && bogstav != " ") antalLiv -= 1

                                    keyboardController?.hide()
                                    if(rollet != 4) rollet = 1

                                }

                            },
                            label = { Text("Gæt et bogstav") },
                            singleLine = true,
                            modifier = Modifier.absoluteOffset(0.dp,10.dp)
                        )
                    } else if(rollet == 1 || rollet == 3){
                        if(!vandt && !tabte) {
                            Button(onClick = {
                                currentRotation = (0..360).random().toFloat()
                                //currentRotation = 83f
                                println(currentRotation)
                                bogstav = ""
                                if (currentRotation > 16.4 && currentRotation < 82f || currentRotation > 98.4f && currentRotation < 246 ||
                                    currentRotation > 262.4f
                                ) rollet = 0
                                else rollet = 1
                            }, modifier = Modifier.absoluteOffset(0.dp, 10.dp)) {
                                Text(text = "Tryk her at spinne lykkehjullet!")
                            }
                        }
                    }

                }
                item {

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

                        if (antalLiv <= 0) {
                            tabte = true
                            Toast.makeText(context, "Øv, du tabte! Indtast dit navn ovenfor og gem din score.", Toast.LENGTH_SHORT).show()
                        }
                        else if (svarFelt.toString() == (svar)) {
                            vandt = true
                            Toast.makeText(context, "Tillykke! Du gættede ordet! Indtast dit navn ovenfor og gem din score.", Toast.LENGTH_SHORT).show()
                            rollet = 4
                        } else rollet = 3
                        //println("$korrekteBogstaver Rigtige, Du har nu: $allPoints point")
                    }


                    Text(text = "Point: $allPoints!",fontSize = 35.sp)

                }

                item {
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

                                    BufferedWriter(FileWriter("$path/test2.txt",true)).use {it.write("$allPoints - $navnInput\n")}

                                    allPoints = 0
                                    vandt = false
                                    tabte = false

                                    navController.popBackStack("HomeScreen",false)}
                            ))
                    }
                }
            }
        }



    }
    return svar
}

//pointsystem som tager lykkehjulets nuværende rotation og tildeler point efter grad
@Composable
private fun pointSystem(rotation : Float): Int {
    var points = 0

    when {
        rotation >= 0 && rotation < 16.4f -> {
            points = 0
            // Fallit
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
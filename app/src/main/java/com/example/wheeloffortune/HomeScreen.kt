package com.example.wheeloffortune

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.Switch
import android.widget.Toast
import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wheeloffortune.ui.theme.WheelOfFortuneTheme
import kotlinx.coroutines.android.awaitFrame
import java.util.*
import kotlin.text.StringBuilder


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

@ExperimentalComposeUiApi
@Composable
fun Greeting(context: Context, navController: NavController) {
    Column(modifier = Modifier.width(500.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Velkommen til Lykkethjulet!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Image(
            painterResource(R.drawable.lykkehjul),
            contentDescription = "Lykkehjul",
            Modifier.padding(0.dp,0.dp,0.dp,20.dp)
        )

        playButton(context = context, navController)
    }
}
@ExperimentalComposeUiApi
@Composable
fun playButton(context: Context, navController: NavController){
    var visible by remember { mutableStateOf(true) }
    if(visible) {
        Button(
            onClick = {
                //Toast.makeText(context, "Finder dit ord", Toast.LENGTH_LONG).show()
                visible = false
                //Toast.makeText(context, "Ord fundet", Toast.LENGTH_LONG).show()
            },
            enabled = true,
        ) {
            Text(text = "Start Spil")
        }
    } else {
        game(navController)
        /*Text(text = "Din kategory er: ", fontSize = 20.sp)
        var bogstav by rememberSaveable { mutableStateOf("")}
        //var svar = game()
        if(bogstav == game()){
            Text("Tillykke du vandt!")
        }
        var inputFelt = TextField(value = bogstav, onValueChange = {bogstav = it}, label = {Text("Gæt et bogstav")})*/
    }
}


@ExperimentalComposeUiApi
@Composable
fun game(navController: NavController): String {
    Text(text = "Din kategory er: ", fontSize = 20.sp)

    var svar by rememberSaveable { mutableStateOf("") }
    var svarFelt: StringBuilder = remember{ StringBuilder() }
    val x = (0..0).random()
    when(x){
        0 -> {
            Text(text = "Navnet på en person \n", fontSize = 20.sp)
            svar = "ok"
            if(svarFelt.isEmpty()){svarFelt.append("__")}

        }
        1 -> Text(text = "_ _ _ _ _ _ _", fontSize = 20.sp)
        2 -> Text(text = "Dette", fontSize = 20.sp)
        3 -> Text(text = "Er", fontSize = 20.sp)
        4 -> Text(text = "Bare", fontSize = 20.sp)
        5 -> Text(text = "En", fontSize = 20.sp)
        6 -> Text(text = "Tissemand", fontSize = 20.sp)
        7 -> Text(text = "Hæhæ", fontSize = 20.sp)
        8 -> Text(text = "Det", fontSize = 20.sp)
        9 -> Text(text = "Var", fontSize = 20.sp)
        10 -> Text(text = "Sjovt", fontSize = 20.sp)
    }
    Text(text = svarFelt.toString(), fontSize = 20.sp)
    var bogstav by rememberSaveable { mutableStateOf("")}
    var vandt by rememberSaveable { mutableStateOf(false)}
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(enabled = true, value = bogstav, onValueChange = {if(bogstav.isEmpty())bogstav = it else if(bogstav.isNotEmpty())bogstav.removeRange(1,bogstav.length) }, label = {Text("Gæt et bogstav")}, singleLine = true, keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone ={
                for (i in svar.indices) {
                    if(bogstav[0] == svar[i]){
                        svarFelt.replace(i,i+1,bogstav[0].toString())
                        println(svarFelt)
                    } else {

                    }
                }
                if(svarFelt.toString().equals(svar)){
                    vandt = true
                }
                bogstav = ""
                keyboardController?.hide()}
        )
    )
    if(vandt){
        navController.navigate("winningScreen")
    }

    return svar
}



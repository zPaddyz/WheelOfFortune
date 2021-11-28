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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.VerticalAlignmentLine
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
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Image(
            painterResource(R.drawable.lykkehjul),
            contentDescription = "Lykkehjul",
            modifier = Modifier
                .padding(0.dp,0.dp,0.dp,10.dp)
                .size(250.dp)
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
    }
}


@ExperimentalComposeUiApi
@Composable
fun game(navController: NavController): String {
    var svar by rememberSaveable { mutableStateOf("") }
    val svarFelt: StringBuilder = remember{ StringBuilder() }
    val randomX by rememberSaveable {mutableStateOf((0..7).random())}
    var bogstav by rememberSaveable { mutableStateOf("")}
    var vandt by rememberSaveable { mutableStateOf(false)}
    var tabte by rememberSaveable { mutableStateOf(false)}
    var antalLiv by rememberSaveable() {mutableStateOf(5)}
    val keyboardController = LocalSoftwareKeyboardController.current
    Row() {
        for (i in 1..antalLiv) {
            Image(
                painterResource(R.drawable.heart),
                contentDescription = "Liv",
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }
    Text(text = "Din kategori er: ", fontSize = 20.sp)


    when(randomX){
        0 -> {
            Text(text = "Et Navn\n", fontSize = 20.sp)
            svar = "Patrick"
            if(svarFelt.isEmpty()){svarFelt.append("_______")}
        }
        1 -> {
            Text(text = "Et Navn \n", fontSize = 20.sp)
            svar = "Trine"
            if(svarFelt.isEmpty()){svarFelt.append("_____")}
        }
        2 -> {
            Text(text = "Et Navn\n", fontSize = 20.sp)
            svar = "Jørgen"
            if(svarFelt.isEmpty()){svarFelt.append("______")}
        }
        3 -> {
            Text(text = "Et Bilmærke \n", fontSize = 20.sp)
            svar = "Volkswagen"
            if(svarFelt.isEmpty()){svarFelt.append("__________")}
        }
        4 -> {
            Text(text = "Et Bilmærke \n", fontSize = 20.sp)
            svar = "Ford"
            if(svarFelt.isEmpty()){svarFelt.append("____")}
        }
        5 -> {
            Text(text = "Et Bilmærke \n", fontSize = 20.sp)
            svar = "Porche"
            if(svarFelt.isEmpty()){svarFelt.append("______")}
        }
        6 -> {
            Text(text = "En Grønsag \n", fontSize = 20.sp)
            svar = "Tomat"
            if(svarFelt.isEmpty()){svarFelt.append("_____")}
        }
        7 -> {
            Text(text = "En Grønsag \n", fontSize = 20.sp)
            svar = "Selleri"
            if(svarFelt.isEmpty()){svarFelt.append("_______")}
        }
    }

    Text(text = svarFelt.toString(), fontSize = 30.sp, letterSpacing = 5.sp)
    TextField(enabled = true, value = bogstav, onValueChange = {if(bogstav.isEmpty())bogstav = it else if(bogstav.isNotEmpty())bogstav.removeRange(1,bogstav.length) }, label = {Text("Gæt et bogstav")}, singleLine = true, keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone ={
                var ingenPasser = true
                if(bogstav.isEmpty() || bogstav[0].isDigit()) bogstav = " "
                for (i in svar.indices) {
                    if(bogstav[0] == svar[i]){
                        svarFelt.replace(i,i+1,bogstav[0].toString())
                        println(svarFelt)
                        ingenPasser = false
                    } else {

                    }
                }
                if(ingenPasser  && bogstav != " ") antalLiv -= 1
                if(antalLiv == 0) tabte = true
                if(svarFelt.toString() == (svar)){
                    vandt = true
                }
                bogstav = ""
                keyboardController?.hide()}
        )
    )
    if(vandt){
        navController.popBackStack("winningScreen",false)
    }
    if(tabte){
        navController.popBackStack("winningScreen",false)
    }

    return svar
}



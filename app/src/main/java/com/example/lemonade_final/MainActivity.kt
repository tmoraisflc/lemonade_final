package com.example.lemonade_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade_final.ui.theme.Lemonade_finalTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lemonade_finalTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar ={
                        //criando a barra amarela do topo
                    CenterAlignedTopAppBar(
                        title={
                            Text(
                                text="Lemonade",
                                fontWeight = FontWeight.Bold,
                                //style = TextStyle(24.dp)  //nao consegui aumentar o tamanho, pq?
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(Color.Yellow)//amarelo da imagem -> como acho a cor mesmo com o MAC???

                    )
                }
                    ) { innerPadding ->
                    LemonadeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class LemonadeStep() {
    TREE, LEMON, LEMONADE, EMPTY_GLASS  // status da tela

}

@Composable
fun LemonadeScreen(modifier: Modifier = Modifier) {
    var lemonadeStep by remember { mutableStateOf(LemonadeStep.TREE) }


    var lemonadeImage = R.drawable.lemon_tree   // nao precisa do primeiro?
    var lemonadeContentDescription = R.string.lemon_tree
    var lemonadeDescription = R.string.tap_lemon_tree


    when (lemonadeStep) {
        LemonadeStep.TREE -> {
            lemonadeImage = R.drawable.lemon_tree
            lemonadeContentDescription = R.string.lemon_tree
            lemonadeDescription = R.string.tap_lemon_tree
        }

        LemonadeStep.LEMON -> {
            lemonadeImage = R.drawable.lemon_squeeze
            lemonadeContentDescription = R.string.lemon
            lemonadeDescription = R.string.tap_squeeze
        }

        LemonadeStep.LEMONADE -> {
            lemonadeImage = R.drawable.lemon_drink
            lemonadeContentDescription = R.string.glass_of_lemonade
            lemonadeDescription = R.string.tap_lemonade_drink
        }

        else -> {
            lemonadeImage = R.drawable.lemon_restart
            lemonadeContentDescription = R.string.empty_glass
            lemonadeDescription = R.string.tap_empty_glass
        }
    }

    Column(
        modifier = modifier
    ) {

        // 1. Estado para controlar em qual passo estamos (1, 2, 3 ou 4)
        var currentStep by remember { mutableStateOf(1) }

// 2. Estado para armazenar quantos cliques faltam
        var squeezeCount by remember { mutableStateOf(0) }



        Button(
            onClick = {
                if (lemonadeStep == LemonadeStep.TREE) {
                    // 1. Antes de ir para o limão, sorteia o número
                    squeezeCount = (2..4).random()
                    lemonadeStep = LemonadeStep.LEMON
                } else if (lemonadeStep == LemonadeStep.LEMON) {
                    // 2. No limão, diminui 1 ponto a cada clique
                    squeezeCount--

                    // 3. SÓ MUDA se a conta chegar a zero
                    if (squeezeCount == 0) {
                        lemonadeStep = LemonadeStep.LEMONADE
                    }
                } else if (lemonadeStep == LemonadeStep.LEMONADE) {
                    lemonadeStep = LemonadeStep.EMPTY_GLASS
                } else {
                    lemonadeStep = LemonadeStep.TREE
                }
            },
            shape = RoundedCornerShape(40.dp), //Bordas bem arredondadas
            modifier = Modifier.padding(top = 240.dp, bottom = 240.dp, start = 100.dp,  end = 100.dp)
        ) { LemonTree(lemonadeImage, lemonadeContentDescription, lemonadeDescription) }
    }
}

@Preview
@Composable
private fun LemonadeScreenPreview(

) {

}


@Composable
fun LemonTree(selectedImage: Int, contentDescriptionImage: Int, description: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            //.padding(all = 12.dp)     //esse padding nao ta mudando nada?
    ) {
        Image(
            painterResource(selectedImage),
            contentDescription = stringResource(contentDescriptionImage),
            modifier = Modifier
                .background(color = Color.Green, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 24.dp)
                .size(220.dp)
        )
        Text(
            stringResource(description),
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun LemonTreePreview() {
    Lemonade_finalTheme {
        LemonTree(
            R.drawable.lemon_drink,
            contentDescriptionImage = R.string.glass_of_lemonade,
            description = R.string.tap_lemonade_drink
        )
    }

}

@Composable
fun Lemonade(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lemonade_finalTheme {
        Lemonade("Android")
    }
}
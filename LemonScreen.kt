package com.example.lemonadeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme

enum class LemonadeStepByStep { TREE, LEMON, LEMONADE, EMPTY_GLASS }

@Composable
fun LemonadeScreen(modifier: Modifier = Modifier) {
    var lemonadeSteps by remember { mutableStateOf(LemonadeStepByStep.TREE) }
    var lemonadeImage: Int
    var lemonadeContentDescription: Int
    var lemonadeDescription: Int
    var count: Int = (2..4).random()
    var click = 1

    when (lemonadeSteps) {
        LemonadeStepByStep.TREE -> {
            lemonadeImage = R.drawable.lemon_tree
            lemonadeContentDescription = R.string.lemon_tree_content_description
            lemonadeDescription = R.string.tap_lemon_tree
        }

        LemonadeStepByStep.LEMON -> {
            lemonadeImage = R.drawable.lemon_squeeze
            lemonadeContentDescription = R.string.lemon_content_description
            lemonadeDescription = R.string.tap_squeeze_lemon
        }

        LemonadeStepByStep.LEMONADE -> {
            lemonadeImage = R.drawable.lemon_drink
            lemonadeContentDescription = R.string.glass_of_lemonade_content_description
            lemonadeDescription = R.string.tap_drink_lemonade
        }

        else -> {
            lemonadeImage = R.drawable.lemon_restart
            lemonadeContentDescription = R.string.empty_glass_content_description
            lemonadeDescription = R.string.tap_empty_glass
        }
    }

    Column(
        modifier.clickable(
            onClick = {
                lemonadeSteps = when (lemonadeSteps) {
                    LemonadeStepByStep.TREE -> LemonadeStepByStep.LEMON
                    LemonadeStepByStep.LEMON -> {
                        if (count == click) {
                            count = (2..4).random()
                            LemonadeStepByStep.LEMONADE
                        }
                        else {
                            click++
                            LemonadeStepByStep.LEMON
                        }
                    }
                    LemonadeStepByStep.LEMONADE -> LemonadeStepByStep.EMPTY_GLASS
                    else -> LemonadeStepByStep.TREE
                }
            }
        )
    ) {
        LemonadeItem(
            image = lemonadeImage,
            contentDescription = lemonadeContentDescription,
            description = lemonadeDescription,
            modifier
        )
    }
}

@Composable
fun LemonadeItem(
    image: Int,
    contentDescription: Int,
    description: Int,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.dp)
    ) {
        Image(
            painterResource(image),
            contentDescription = stringResource(contentDescription),
            modifier
                .background(color = Color.Gray, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 24.dp)
                .size(220.dp)

        )
        Text(
            stringResource(description),
            modifier.padding(top = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeItemPreview() {
    LemonadeAppTheme {
        Column() {
            LemonadeItem(
                R.drawable.lemon_tree,
                R.string.lemon_tree_content_description,
                R.string.tap_lemon_tree,
                modifier = Modifier
            )
        }
    }
}
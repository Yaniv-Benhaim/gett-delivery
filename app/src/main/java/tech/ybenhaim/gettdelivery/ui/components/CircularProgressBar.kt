package tech.ybenhaim.gettdelivery.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if(isDisplayed) {
        CircularProgressIndicator()
    }
}
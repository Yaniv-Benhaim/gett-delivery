package tech.ybenhaim.gettdelivery.ui.components.elements.progressbars

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable


@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    if(isDisplayed) {
        CircularProgressIndicator()
    }
}
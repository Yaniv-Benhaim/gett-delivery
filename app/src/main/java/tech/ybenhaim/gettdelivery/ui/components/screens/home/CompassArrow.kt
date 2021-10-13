package tech.ybenhaim.gettdelivery.ui.components.screens.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel

@ExperimentalCoroutinesApi
@Composable
fun CompassArrow(viewModel: MainViewModel) {

    val rotation by remember { viewModel.currentAzimuth }
    val (lastRotation, setLastRotation) = remember { mutableStateOf(0) } // this keeps last rotation
    var newRotation = lastRotation // newRotation will be updated in proper way
    val modLast = if (lastRotation > 0) lastRotation % 360 else 360 - (-lastRotation % 360) // last rotation converted to range [-359; 359]

    if (modLast != rotation) // if modLast isn't equal rotation retrieved as function argument it means that newRotation has to be updated
    {
        val backward = if (rotation > modLast) modLast + 360 - rotation else modLast - rotation // distance in degrees between modLast and rotation going backward
        val forward = if (rotation > modLast) rotation - modLast else 360 - modLast + rotation // distance in degrees between modLast and rotation going forward

        // update newRotation so it will change rotation in the shortest way
        newRotation = if (backward < forward)
        {
            // backward rotation is shorter
            lastRotation - backward
        }
        else
        {
            // forward rotation is shorter (or they are equal)
            lastRotation + forward
        }

        setLastRotation(newRotation)
    }

    val angle: Float by animateFloatAsState(
        targetValue = -newRotation.toFloat(),
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    )


    Image(
        painter = painterResource(id = R.drawable.navigation),
        contentDescription = "compass",
        modifier = Modifier
            .rotate(angle)
            .height(50.dp)
            .width(50.dp)
    )

}

@Preview
@Composable
fun PrevCompass() {

}
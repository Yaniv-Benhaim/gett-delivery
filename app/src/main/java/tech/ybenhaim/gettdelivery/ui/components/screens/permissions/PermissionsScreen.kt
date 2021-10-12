package tech.ybenhaim.gettdelivery.ui.components.screens.permissions

import android.app.Activity
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import tech.ybenhaim.gettdelivery.MainActivity
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.util.tracking.TrackingUtility


@Composable
fun PermissionsScreen(navController: NavController) {
    val activity = LocalContext.current as MainActivity
    val context = LocalContext.current

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        if(TrackingUtility.hasLocationPermissions(context)) {
            navController.navigate("home")
        } else {
            activity.requestPermissions()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gett_logo_big),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )

        Text(
            text = "To run this app without any issues we need permissions to track your current location",
            maxLines = 3,
            modifier = Modifier.scale(scale.value)
        )
    }
}


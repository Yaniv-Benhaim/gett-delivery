package tech.ybenhaim.gettdelivery.ui.components.info

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.ui.theme.DarkYellowGett
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700
import tech.ybenhaim.gettdelivery.ui.theme.YellowGett
import tech.ybenhaim.gettdelivery.util.deliveries.getTaskTitle


@Composable
fun InfoScreen(pickupPoint: String, task: String) {

    Card(

        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(Purple700, Purple500)))
                .padding(12.dp)

        ) {
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = task.getTaskTitle(),
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = pickupPoint,
                maxLines = 1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
        }

    }
}

@Preview
@Composable
fun PreviewInfo() {
    InfoScreen("Tel Aviv Shenkar steet 10", "Pickup")
}
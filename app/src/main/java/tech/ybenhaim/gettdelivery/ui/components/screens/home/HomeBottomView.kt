package tech.ybenhaim.gettdelivery.ui.components.screens.home

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.outlined.Home
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.ybenhaim.gettdelivery.R
import tech.ybenhaim.gettdelivery.data.Constants
import tech.ybenhaim.gettdelivery.ui.components.elements.buttons.WhiteShadowButton
import tech.ybenhaim.gettdelivery.ui.components.elements.text.LeftTitle
import tech.ybenhaim.gettdelivery.ui.theme.GreyExtraLight
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700

@Composable
fun HomeBottomView(onClick: () -> Unit) {

    val image: Painter = painterResource(id = R.drawable.ic_baseline_local_taxi_24)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                color = GreyExtraLight,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            )
            
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = image,
                contentDescription = "horn",
                modifier = Modifier
                    .padding(start = 24.dp)
                    .height(28.dp)
                    .width(28.dp)

            )
            Text(
                text = "Enjoy your ride",
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 15.dp, top = 15.dp)
            )
        }
        

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )
                .padding(bottom = 30.dp)

            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)


            ) {

                WhiteShadowButton(
                    text = Constants.ARRIVED_BUTTON_TEXT,
                    textColor = Color.White,
                    gradient =  Brush.horizontalGradient(listOf(Purple700, Purple500)),
                    onClick = onClick,
                )

            }

        }
    }



}

@Preview
@Composable
fun Prev() {
    HomeBottomView(onClick = {})
}
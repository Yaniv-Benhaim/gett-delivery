package tech.ybenhaim.gettdelivery.ui.components.elements.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700

@Composable
fun CenteredTitle(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .background(Brush.horizontalGradient(listOf(Purple700, Purple500)))
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(12.dp)
            )
           
        }
    }
}

@Composable
fun LeftTitle(title: String) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .padding(horizontal = 6.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                maxLines = 1,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

        }
    }
}


@Preview
@Composable
fun PrevTitle() {
    CenteredTitle(title = "Parcels to collect")
    
}
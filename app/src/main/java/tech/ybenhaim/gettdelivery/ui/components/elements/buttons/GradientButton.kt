package tech.ybenhaim.gettdelivery.ui.components.elements.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.ybenhaim.gettdelivery.ui.theme.DarkYellowGett
import tech.ybenhaim.gettdelivery.ui.theme.YellowGett

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
           backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        modifier = Modifier.padding(50.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .height(20.dp),
            contentAlignment = Alignment.Center

        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Composable
fun WhiteShadowButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .shadow(8.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .height(40.dp),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily.Default
            )
        }
    }
}

@Preview
@Composable
fun GradientPreview() {
    WhiteShadowButton(
        text = "Arrived",
        textColor = Color.Black,
        gradient = Brush.horizontalGradient(
            colors = listOf(Color.White, Color.White)
        )
    ) {
        
    }
}
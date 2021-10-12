package tech.ybenhaim.gettdelivery.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700

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
        onClick = { onClick }
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

@Preview
@Composable
fun GradientPreview() {
    GradientButton(
        text = "Arrived",
        textColor = Color.White,
        gradient = Brush.horizontalGradient(
            colors = listOf(Purple700, Purple500)
        )
    ) {
        
    }
}
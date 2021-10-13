package tech.ybenhaim.gettdelivery.ui.components.screens.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.ybenhaim.gettdelivery.data.Constants
import tech.ybenhaim.gettdelivery.ui.components.elements.buttons.GradientButton
import tech.ybenhaim.gettdelivery.ui.components.elements.dialog.DeliveryFinishedDialog
import tech.ybenhaim.gettdelivery.ui.components.elements.text.CenteredTitle
import tech.ybenhaim.gettdelivery.ui.components.elements.text.LeftTitle
import tech.ybenhaim.gettdelivery.ui.components.screens.pickup.ParcelItem
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700
import tech.ybenhaim.gettdelivery.util.deliveries.getTaskTitle
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel

@ExperimentalCoroutinesApi
@Composable
fun HistoryScreen(viewModel: MainViewModel, navController: NavHostController) {


    val history by remember { viewModel.currentHistory }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            CenteredTitle(title = "History")
        }

        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.Start)
            ) {
                Text(text = "Total packages delivered: 6", style = MaterialTheme.typography.h6)
                Text(text = "Total packages delivered: 6", style = MaterialTheme.typography.caption)
            }
        }


        LeftTitle(
            title = "Great job!"
        )


        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = viewModel.navigationRoutes.value[0].parcels,
                itemContent = {
                    ParcelItem(parcel = it)
                }
            )
        }
    }




}
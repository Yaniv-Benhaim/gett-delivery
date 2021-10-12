package tech.ybenhaim.gettdelivery.ui.components.screens.pickup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.ybenhaim.gettdelivery.data.Constants.PARCELS_TO_COLLECT
import tech.ybenhaim.gettdelivery.data.Constants.PARCELS_TO_DELIVER
import tech.ybenhaim.gettdelivery.data.Constants.PICKUP_PARCELS
import tech.ybenhaim.gettdelivery.ui.components.buttons.GradientButton
import tech.ybenhaim.gettdelivery.ui.components.dialog.DeliveryFinishedDialog
import tech.ybenhaim.gettdelivery.ui.components.text.CenteredTitle
import tech.ybenhaim.gettdelivery.ui.components.text.LeftTitle
import tech.ybenhaim.gettdelivery.ui.theme.Purple500
import tech.ybenhaim.gettdelivery.ui.theme.Purple700
import tech.ybenhaim.gettdelivery.util.deliveries.getTaskTitle
import tech.ybenhaim.gettdelivery.viewmodels.MainViewModel
import timber.log.Timber


@ExperimentalCoroutinesApi
@Composable
fun PickupScreen(viewModel: MainViewModel, navController: NavHostController) {

    val currentTask by remember { viewModel.currentTask }
    val isLastTask by remember { viewModel.isLastTask }
    val title = currentTask.type

    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            CenteredTitle(title = title.getTaskTitle())
        }


        LeftTitle(
            title = if (title == PICKUP_PARCELS)
                PARCELS_TO_COLLECT else PARCELS_TO_DELIVER
        )


        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = currentTask.parcels ?: emptyList(),
                itemContent = {
                    ParcelItem(parcel = it)
                }
            )
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 60.dp)
            .fillMaxSize()
    ) {
        GradientButton(
            text = "Done",
            textColor = Color.White,
            gradient =  Brush.horizontalGradient(listOf(Purple700, Purple500)),
            onClick = {
                if(currentTask != viewModel.lastDeliveryTask.value) {
                    navController.navigate("home")
                    viewModel.getNextTask()
                } else {
                    viewModel.isLastTask.value = true
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DeliveryFinishedDialog(showDialog = isLastTask, navController = navController)
    }
    
    
}


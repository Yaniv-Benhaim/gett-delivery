package tech.ybenhaim.gettdelivery.ui.components.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun DeliveryFinishedDialog(showDialog: Boolean, navController: NavController) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Delivery Completed"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                       navController.navigate("history")
                    },

                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Delivery overview"
                    )
                }
            },
            dismissButton = {},
            text = {
                Text(
                    textAlign = TextAlign.Left,
                    text ="Amazing you have finished all your deliveries for today!"
                )
            },

        )
    }
}

@Composable
fun NeedsPermissionsDialog(showDialog: Boolean, navController: NavController) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Location Permissions"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
//                        navController.navigate("history")
                    },

                    ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Give permission"
                    )
                }
            },
            dismissButton = {},
            text = {
                Text(
                    textAlign = TextAlign.Left,
                    text ="This app needs permission to track your location to use it's core functionality"
                )
            },

            )
    }
}
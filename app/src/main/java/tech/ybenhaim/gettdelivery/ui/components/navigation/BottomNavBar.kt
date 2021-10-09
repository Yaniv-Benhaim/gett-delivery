package tech.ybenhaim.gettdelivery.ui.components.navigation


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import tech.ybenhaim.gettdelivery.data.models.BottomNavItem

@ExperimentalAnimationApi
@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp
    ) {
       items.forEach { item ->
           val selected = item.route == backStackEntry.value?.destination?.route
           val contentColor = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
           BottomNavigationItem(
               selected = selected,
               onClick = { onItemClick(item) },
               selectedContentColor = MaterialTheme.colors.primary,
               unselectedContentColor = MaterialTheme.colors.onBackground,
               icon = {
                   Row(
                       modifier = Modifier.padding(12.dp),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.spacedBy(4.dp)
                   ) {
                       Icon(
                           imageVector = item.icon,
                           contentDescription = null,
                           tint = contentColor
                       )

                       AnimatedVisibility(visible = selected) {
                           Text(
                               text = item.name,
                               color = contentColor,
                               fontSize = 10.sp
                           )
                       }
                   }
               }
           )
       }
    }

//    val items=Screen.Items.list
//    Row(
//        modifier = Modifier
//            .background(MaterialTheme.colors.background)
//            .padding(8.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically
//
//    ) {
//        items.forEach { item ->
//            BottomNavBarItem(item = item, isSelected = item.id==currentScreenId) {
//               onItemSelected(item)
//            }
//        }
//    }
}

@ExperimentalAnimationApi
@Composable
fun BottomNavBarItem(item:Screen, isSelected: Boolean, onClick:()->Unit) {

    val background = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick))

    {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = contentColor
            )
            
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = item.title,
                    color = contentColor
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun Prev1() {

}

@ExperimentalAnimationApi
@Preview
@Composable
fun Prev2 () {

}
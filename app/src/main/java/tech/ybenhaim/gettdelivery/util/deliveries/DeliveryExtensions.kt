package tech.ybenhaim.gettdelivery.util.deliveries

import tech.ybenhaim.gettdelivery.data.Constants.DROP_OFF_PARCELS
import tech.ybenhaim.gettdelivery.data.Constants.DROP_OFF_PARCELS_TITLE
import tech.ybenhaim.gettdelivery.data.Constants.FINISHED_TASKS
import tech.ybenhaim.gettdelivery.data.Constants.NAVIGATE_TO_DROP_OFF
import tech.ybenhaim.gettdelivery.data.Constants.NAVIGATE_TO_DROP_OFF_TITLE
import tech.ybenhaim.gettdelivery.data.Constants.NAVIGATE_TO_PICKUP
import tech.ybenhaim.gettdelivery.data.Constants.NAVIGATE_TO_PICKUP_TITLE
import tech.ybenhaim.gettdelivery.data.Constants.PICKUP_PARCELS
import tech.ybenhaim.gettdelivery.data.Constants.PICKUP_PARCELS_TITLE

//Extension to determine correct title for each task
fun String.getTaskTitle() : String = when(this) {
    NAVIGATE_TO_PICKUP -> NAVIGATE_TO_PICKUP_TITLE
    PICKUP_PARCELS -> PICKUP_PARCELS_TITLE
    NAVIGATE_TO_DROP_OFF -> NAVIGATE_TO_DROP_OFF_TITLE
    DROP_OFF_PARCELS  -> DROP_OFF_PARCELS_TITLE
    else  -> FINISHED_TASKS
}



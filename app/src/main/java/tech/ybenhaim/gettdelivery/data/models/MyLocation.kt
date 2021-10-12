package tech.ybenhaim.gettdelivery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nav_items")
data class MyLocation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val bearing: Float,
    val speed: Float
)
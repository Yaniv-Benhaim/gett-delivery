package tech.ybenhaim.gettdelivery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History
    (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val parcels_delivered: Int = 0,
        val kilometers_travelled: Int = 0,
        val average_parcels_per_day: Int = 0
    )
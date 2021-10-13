package tech.ybenhaim.gettdelivery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.ybenhaim.gettdelivery.data.models.History
import tech.ybenhaim.gettdelivery.data.models.Location
import tech.ybenhaim.gettdelivery.data.models.MyLocation

/*
    Database for saving Location Points received from TrackingService locally -
    and later on releasing them as a flow of points for easy collection.
*/

private const val DB_NAME = "location_database"

@Database(entities = [MyLocation::class, History::class], version = 5)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun historyDao(): HistoryDao

    companion object {
        fun create(context: Context): LocationDatabase {

            return Room.databaseBuilder(
                context,
                LocationDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
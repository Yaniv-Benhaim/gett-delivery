package tech.ybenhaim.gettdelivery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.ybenhaim.gettdelivery.data.models.Location
import tech.ybenhaim.gettdelivery.data.models.MyLocation

private const val DB_NAME = "location_database"

@Database(entities = [MyLocation::class], version = 4)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

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
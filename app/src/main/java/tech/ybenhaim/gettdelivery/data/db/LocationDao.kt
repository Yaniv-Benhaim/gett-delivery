package tech.ybenhaim.gettdelivery.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.ybenhaim.gettdelivery.data.models.Location
import tech.ybenhaim.gettdelivery.data.models.MyLocation

@Dao
interface LocationDao {

    /*
    This interface is used to store Location Points received from -
    Tracking Service & release them as a flow for easy collection
     */

    //Added the Delete locations within this function to keep the database small
    @Transaction
    suspend fun updateLocation(location: MyLocation) {
        location.let {
            deleteLocations()
            insertLocation(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(location: MyLocation)

    @Query("DELETE FROM nav_items")
    suspend fun deleteLocations()

    @Transaction
    suspend fun updateCurrentLocation(location: MyLocation) {
        insertNavigationItem(location)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNavigationItem(navRoute: MyLocation)

    @Query("SELECT * FROM nav_items ORDER BY id")
    fun getCurrentLocation(): Flow<List<MyLocation>>
}
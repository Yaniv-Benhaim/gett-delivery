package tech.ybenhaim.gettdelivery.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.ybenhaim.gettdelivery.data.models.Location
import tech.ybenhaim.gettdelivery.data.models.MyLocation

@Dao
interface LocationDao {

    @Transaction
    suspend fun updateLocation(location: MyLocation) {
        location.let {
            deleteLocations() // This deletes previous locations to keep the database small. If you want to store a full location history, remove this line.
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
package tech.ybenhaim.gettdelivery.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tech.ybenhaim.gettdelivery.data.models.History
import tech.ybenhaim.gettdelivery.data.models.MyLocation

@Dao
interface HistoryDao {

/*
    This interface is used to keep track of the user's history.
    Could definitely be extended to keep track of a lot more.
*/

    @Update
    suspend fun updateHistory(history: History)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: History)

    @Query("SELECT * FROM history_table ORDER BY id")
    fun getCurrentHistory(): Flow<History>
}
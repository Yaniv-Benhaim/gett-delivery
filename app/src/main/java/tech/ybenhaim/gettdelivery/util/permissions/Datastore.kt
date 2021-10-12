package tech.ybenhaim.gettdelivery.util.permissions

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.ybenhaim.gettdelivery.dataStore
import tech.ybenhaim.gettdelivery.util.Constants

fun Context.isFirstRun() : Flow<Boolean> {
    val IS_FIRST_RUN = booleanPreferencesKey(Constants.IS_FIRST_RUN)
    val isFirstRunFlow: Flow<Boolean> = this.dataStore.data
        .map { preferences ->
            preferences[IS_FIRST_RUN] ?: true
        }
    return isFirstRunFlow
}
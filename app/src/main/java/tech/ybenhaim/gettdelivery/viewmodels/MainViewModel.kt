package tech.ybenhaim.gettdelivery.viewmodels

import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tech.ybenhaim.gettdelivery.data.models.MyLocation
import tech.ybenhaim.gettdelivery.data.remote.responses.Geo
import tech.ybenhaim.gettdelivery.data.remote.responses.NavigationRoute
import tech.ybenhaim.gettdelivery.repository.GettRepository
import tech.ybenhaim.gettdelivery.util.Resource
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GettRepository
    ) : ViewModel() {

    var navigationRoutes = mutableStateOf(NavigationRoute())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var currentLocation = mutableStateOf(MyLocation(0.0,0.0,0f,0f))

    fun loadDeliveryRoute() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getDeliveryRoute()
            when(result) {
                is Resource.Success -> {
                   result.data?.let {
                       navigationRoutes.value = result.data
                       Timber.d("JOURNEY: ${result.data}")
                   }
                }
                is Resource.Error -> {


                }
            }
        }
    }

    fun updateCurrentLocation(newLocation: Location) {
        val updatedLocation = MyLocation(
            newLocation.latitude,
            newLocation.longitude,
            newLocation.bearing,
            newLocation.speed
        )
        currentLocation.value = updatedLocation
    }
}
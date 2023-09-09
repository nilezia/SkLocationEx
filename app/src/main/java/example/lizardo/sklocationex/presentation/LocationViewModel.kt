package example.lizardo.sklocationex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.domain.GetLocationUseCase
import example.lizardo.sklocationex.domain.SendDataToSocketUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private var getLocationUseCase: GetLocationUseCase,
    private var sendSocketUseCase: SendDataToSocketUseCase
) : ViewModel() {

    var onUpdateLocation = MutableLiveData<Location>()
    var onSocketStatus = MutableLiveData<String>()
    fun getLocationUpdate() {
        viewModelScope.launch {
            getLocationUseCase.execute().flowOn(Dispatchers.IO).catch { }.collect {
                onUpdateLocation.value = it
            }
        }
    }

    fun sendSocket(location: Location) {
        viewModelScope.launch {
            sendSocketUseCase.execute(location).flowOn(Dispatchers.IO).catch { }.collect {
                onSocketStatus.value = it
            }
        }
    }
}
package example.lizardo.sklocationex.data.repository

import android.util.Log
import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.manager.LocationManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface LocationRepository {
    fun getLocation(): Flow<Location>
}

class LocationRepositoryImpl @Inject constructor() :
    LocationRepository {

    override fun getLocation(): Flow<Location> {
        return flow {
           /* locationManager.initial()
            locationManager.getLocation().first()?.let { location ->
                Log.d("ttt", "${location.latitude}, ${location.longitude}")
                emit(location)
            }*/
        }
    }
}
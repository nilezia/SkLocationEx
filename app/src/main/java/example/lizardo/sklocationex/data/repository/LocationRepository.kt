package example.lizardo.sklocationex.data.repository

import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.manager.BaseLocationClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface LocationRepository {
    fun getLocation(): Flow<Location>
}

class LocationRepositoryImpl @Inject constructor() :
    LocationRepository {

    override fun getLocation(): Flow<Location> {
       // var baselocationClient = BaseLocationClient()
        return flow {
           /* locationManager.initial()
            locationManager.getLocation().first()?.let { location ->
                Log.d("ttt", "${location.latitude}, ${location.longitude}")
                emit(location)
            }*/
        }
    }
}
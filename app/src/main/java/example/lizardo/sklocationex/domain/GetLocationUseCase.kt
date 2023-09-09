package example.lizardo.sklocationex.domain

import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetLocationUseCase {
    fun execute(): Flow<Location>
}

class GetLocationUseCaseImpl @Inject constructor(private var locationRepository: LocationRepository) :
    GetLocationUseCase {
    override fun execute(): Flow<Location> {

        return flow {
            locationRepository.getLocation().collect {
                emit(it)
            }
        }
    }
}
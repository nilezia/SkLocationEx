package example.lizardo.sklocationex.domain

import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.data.repository.SocketServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SendDataToSocketUseCase {
    fun execute(location: Location): Flow<String>
}

class SendDataToSocketUseCaseImpl @Inject constructor(private var socketServiceRepository: SocketServiceRepository) :
    SendDataToSocketUseCase {
    override fun execute(location: Location): Flow<String> {
        return flow {
            socketServiceRepository.sendDataToTCPSocket(location).collect{
                emit(it)
            }
        }
    }
}
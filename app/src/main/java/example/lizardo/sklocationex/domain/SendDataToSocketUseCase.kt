package example.lizardo.sklocationex.domain

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import example.lizardo.sklocationex.LocationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SendDataToSocketUseCase {
    fun execute(): Flow<String>
}

class SendDataToSocketUseCaseImpl @Inject constructor(
    @ApplicationContext private var context: Context
) :
    SendDataToSocketUseCase {
    override fun execute(): Flow<String> {
        val intent = Intent(context, LocationService::class.java)
        context.startService(intent)
        return flow {
            /*socketServiceRepository.sendDataToTCPSocket(location).collect {}*/
                emit("test")

        }
    }
}
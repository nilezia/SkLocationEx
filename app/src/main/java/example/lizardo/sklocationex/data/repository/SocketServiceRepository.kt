package example.lizardo.sklocationex.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.OutputStream
import java.net.Socket
import javax.inject.Inject

interface SocketServiceRepository {

    fun sendDataToTCPSocket(locationData: String): Flow<String>
}

class SocketServiceRepositoryImpl @Inject constructor() : SocketServiceRepository {
    override fun sendDataToTCPSocket(locationData: String): Flow<String> {
        return sendDataToServer(locationData)
    }

    private fun sendDataToServer(data: String): Flow<String> = flow {
        // Replace with your server IP and port
        val serverIp = "27.254.207.172"
        val serverPort = 5000

        try {
            val socket = Socket(serverIp, serverPort)
            val outputStream: OutputStream = socket.getOutputStream()

            // Send data to the server
            outputStream.write(data.toByteArray())

            // Close the socket and output stream when done
            outputStream.close()
            socket.close()

            // Emit a success message
            emit(data)
        } catch (e: Exception) {
            // Handle any exceptions that may occur
            emit("Error: ${e.message}")
        }
    }
        .flowOn(Dispatchers.IO)
}
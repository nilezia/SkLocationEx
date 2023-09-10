package example.lizardo.sklocationex.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.room.Room
import example.lizardo.sklocationex.ApplicationDatabase
import example.lizardo.sklocationex.data.database.LocationData
import example.lizardo.sklocationex.data.repository.SocketServiceRepository
import example.lizardo.sklocationex.data.repository.SocketServiceRepositoryImpl
import example.lizardo.sklocationex.manager.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class SendDataService : Service() {
    private lateinit var socketServiceRepository: SocketServiceRepository
    private var isNetworkAvailable: NetworkManager? = null
    private lateinit var db: ApplicationDatabase
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        socketServiceRepository = SocketServiceRepositoryImpl()
        db = Room.databaseBuilder(
            applicationContext,
            ApplicationDatabase::class.java, "locationdata"
        ).build()
        isNetworkAvailable = NetworkManager(this)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler.scheduleAtFixedRate({

            if (isNetworkAvailable?.checkNetworkAvailable() == true) {
                getDatabase()
            }
        }, 0, 15, TimeUnit.SECONDS)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun getDatabase() {

        serviceScope.launch {
            val locationDao = db.locationDao()
            val locations: List<LocationData> = locationDao.getAll()
            locations.forEach { locationData ->
                locationData.jsonData?.let {
                    sendDataToSocket(it)

                }
            }
        }
    }

    private suspend fun sendDataToSocket(jsonString: String) {
        socketServiceRepository.sendDataToTCPSocket(
            jsonString
        ).collect {
            val locationDao = db.locationDao()
            locationDao.deleteAllData()
            Log.d("LocationService", it)
        }
    }
}
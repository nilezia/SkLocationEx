package example.lizardo.sklocationex.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import example.lizardo.sklocationex.ApplicationDatabase
import example.lizardo.sklocationex.R
import example.lizardo.sklocationex.data.database.LocationData
import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.getBatteryPct
import example.lizardo.sklocationex.manager.BaseLocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Calendar

class LocationService :
    Service() {

    private val TAG = "LocationService"
    private lateinit var locationManager: BaseLocationClient
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var db: ApplicationDatabase
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d(
            TAG,
            "On Create"
        )
        locationManager = BaseLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        db = Room.databaseBuilder(
            applicationContext,
            ApplicationDatabase::class.java, "locationdata"
        ).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setSettingsText("running location...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)
            .setContentText("Location null...")

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationManager.getLocationUpdate(30000L)
            .catch {}
            .onEach {
                Log.d("LocationService", "${it.latitude}, ${it.longitude}")
                val updateNotification =
                    notification.setContentText("${it.latitude}, ${it.longitude}")
                val locationModel = Location(
                    latitude = it.longitude,
                    longitude = it.longitude,
                    direction = it.bearing,
                    date = Calendar.getInstance().time.toString(),
                    battery = getBatteryPct()
                )
                val packData = Gson().toJson(locationModel)
                saveDataToDataBase(packData)

                notificationManager.notify(1, updateNotification.build())
            }.launchIn(serviceScope)
        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceScope.cancel()
        stopSelf()
    }

    private fun saveDataToDataBase(packData: String) {
        val locationDao = db.locationDao()
        val locationData = LocationData(
            jsonData = packData
        )
        locationDao.insertAll(locationData)
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}
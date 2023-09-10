package example.lizardo.sklocationex

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.data.repository.SocketServiceRepository
import example.lizardo.sklocationex.data.repository.SocketServiceRepositoryImpl
import example.lizardo.sklocationex.manager.BaseLocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Calendar
import javax.inject.Inject

class LocationService :
    Service() {

    private val TAG = "LocationService"
    private lateinit var locationManager: BaseLocationClient
    private lateinit var socketServiceRepository: SocketServiceRepository
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
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
        socketServiceRepository = SocketServiceRepositoryImpl()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "Location")
            .setSettingsText("running location...")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)
            .setContentText("Location null...")

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationManager.getLocationUpdate(15000L)
            .catch {

            }
            .onEach {
                Log.d("LocationService", "${it.latitude}, ${it.longitude}")
                val updateNotification = notification.setContentText("${it.latitude}, ${it.longitude}")
                socketServiceRepository.sendDataToTCPSocket(
                    Location(
                        latitude = it.longitude,
                        longitude = it.longitude,
                        direction = it.bearing,
                        date = Calendar.getInstance().time.toString(),
                        battery = getBatteryPct()
                    )
                ).collect {
                    Log.d("LocationService", "${it}")
                }
                notificationManager.notify(1, updateNotification.build())
            }.launchIn(serviceScope)
        startForeground(1, notification.build())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        stopSelf()
    }
}
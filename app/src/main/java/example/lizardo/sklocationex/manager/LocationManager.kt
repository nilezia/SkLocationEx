package example.lizardo.sklocationex.manager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import example.lizardo.sklocationex.data.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.concurrent.TimeUnit

class LocationManager(private var context: Context, private var updateUi: (Location) -> Unit) {
    companion object {

    }

    private val locationInterval = 30000L
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val locationUpdatesFlow = MutableStateFlow<Location?>(null)
    private lateinit var locationRequest: LocationRequest

    @SuppressLint("MissingPermission")
    fun initial() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, locationInterval)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(locationInterval)
                .setMaxUpdateDelayMillis(locationInterval)
                .build()

        startLocationUpdates()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (location in p0.locations) {
                updateUi.invoke(
                    Location(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        direction = location.bearing,
                        date = Calendar.getInstance().time.toString()
                    )
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}
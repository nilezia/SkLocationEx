package example.lizardo.sklocationex

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import example.lizardo.sklocationex.data.model.Location
import example.lizardo.sklocationex.databinding.ActivityMainBinding
import example.lizardo.sklocationex.manager.LocationManager
import example.lizardo.sklocationex.presentation.LocationViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: LocationViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private val updateUi: (Location) -> Unit = { location ->
        binding.textMain.text = "${location.latitude}, ${location.longitude}"
        val batteryPct = getBatteryPct()
        location.battery = batteryPct
        viewModel.sendSocket(location)
    }

    private fun getBatteryPct(): Float {
        val batteryStatus = getBatteryStatus()
        return batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        } ?: -1f
    }


    private fun getBatteryStatus() = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        this.registerReceiver(null, ifilter)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        // viewModel.getLocationUpdate()
        locationManager = LocationManager(this, updateUi)
        locationManager.initial()

    }

    private fun initViewModel() {
        viewModel.onSocketStatus.observe(this) {
            Log.d("testSocket", "$it")
        }

        viewModel.onUpdateLocation.observe(this) {
            Log.d("testSocket", "${it.latitude}, ${it.longitude}")
            viewModel.sendSocket(it)
        }
    }

    override fun onResume() {
        super.onResume()
        locationManager.startLocationUpdates()
    }
}
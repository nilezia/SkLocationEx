package example.lizardo.sklocationex

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import example.lizardo.sklocationex.databinding.ActivityMainBinding
import example.lizardo.sklocationex.presentation.LocationViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: LocationViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        //  startService(Intent(this,LocationService::class.java))
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),0
        )
        viewModel.sendSocket()
    }

    private fun initViewModel() {
        viewModel.onSocketStatus.observe(this) {
            Log.d("testSocket", "$it")
        }

        viewModel.onUpdateLocation.observe(this) {
            Log.d("testSocket", "${it.latitude}, ${it.longitude}")
            viewModel.sendSocket()
        }

    }

    override fun onResume() {
        super.onResume()
        //locationManager.startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
    }
}
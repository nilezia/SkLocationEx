package example.lizardo.sklocationex.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import example.lizardo.sklocationex.databinding.ActivityMainBinding
import example.lizardo.sklocationex.service.LocationService
import example.lizardo.sklocationex.service.SendDataService


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
            ), 0
        )
    }

    private fun initView() {
        binding.startButton.setOnClickListener {
            startService(Intent(this, LocationService::class.java).apply {
                action = LocationService.ACTION_START
            })
            startService(Intent(this, SendDataService::class.java))
        }
        binding.stopButton.setOnClickListener {
            startService(Intent(this, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
            })
        }
    }
}
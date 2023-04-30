package dev.agmzcr.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import dev.agmzcr.weather.R
import dev.agmzcr.weather.databinding.ActivityMainBinding
import dev.agmzcr.weather.util.AppUtils
import dev.agmzcr.weather.util.Constants.BACKGROUND_LOCATION_PERMISSION_INDEX
import dev.agmzcr.weather.util.Constants.LOCATION_PERMISSION_INDEX
import dev.agmzcr.weather.util.Constants.REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_REQUEST_CODE
import dev.agmzcr.weather.util.Constants.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
import dev.agmzcr.weather.util.EventObserver
import dev.agmzcr.weather.util.State
import dev.agmzcr.weather.util.asResourceId
import dev.agmzcr.weather.util.hide
import dev.agmzcr.weather.util.show
import dev.agmzcr.weather.util.showToast
import java.util.Locale
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        setupUI()
        setupLocation()
        observeAPICall()
    }

    private fun setupLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    viewModel.onLocationUpdated(location)
                } else {
                    fusedLocationClient.requestLocationUpdates(
                        getLocationRequest(),
                        getLocationCallback(),
                        Looper.getMainLooper()
                    )
                }

            }
        }
    }

    private fun getLocationCallback(): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                return viewModel.onLocationUpdated(locationResult.lastLocation!!)
            }
        }
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun requestLocationPermission() {
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        var requestCode = REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
            requestCode = REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_REQUEST_CODE
        }
        requestPermissions(permissionsArray, requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (
            grantResults.isEmpty() ||
            grantResults[LOCATION_PERMISSION_INDEX] == PackageManager.PERMISSION_DENIED ||
            (requestCode == REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_REQUEST_CODE &&
                    grantResults[BACKGROUND_LOCATION_PERMISSION_INDEX] ==
                    PackageManager.PERMISSION_DENIED)
        ) {
            showToast("No permission")
        } else {
            setupLocation()
        }
    }

    private fun observeAPICall() {
        viewModel.weatherLiveData.observe(this, EventObserver { state ->
            when (state) {
                is State.Loading -> {}
                is State.Success -> {
                    state.data.let { weatherDataResponse ->
                        binding.nameCity.text = "${weatherDataResponse.name}, ${weatherDataResponse.sys.country}"
                        binding.currentTemp.text = weatherDataResponse.main.temp.toString().substringBefore(".") + "°C"
                        binding.todayDate.text = AppUtils.fromTimestap(weatherDataResponse.dt)
                        val iconCode = weatherDataResponse.weather[0].icon.asResourceId()
                        binding.weatherIcon.setImageResource(iconCode)
                        binding.weatherDescription.text = weatherDataResponse.weather[0].description.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }
                        binding.maxTempIcon.show()
                        binding.maxTemp.text = weatherDataResponse.main.tempMax.toString().substringBefore(".") + "°C"
                        binding.minTempIcon.show()
                        binding.minTemp.text = weatherDataResponse.main.tempMin.toString().substringBefore(".") + "°C"
                        binding.humidityValue.text = weatherDataResponse.main.humidity.toString().substringBefore(".") + "%"
                        binding.windValue.text = weatherDataResponse.wind.speed.toString().substringBefore(".") + " m/s"
                        binding.pressureValue.text = weatherDataResponse.main.pressure.toString().substringBefore(".") + " hPa"
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                }
            }
        })
    }

    private fun setupUI() {
        binding.inputFindCityWeather.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.fetchWeather((view as EditText).text.toString())
            }
            false
        }
    }
}
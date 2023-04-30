package dev.agmzcr.weather.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.agmzcr.weather.data.model.ForecastDataResponse
import dev.agmzcr.weather.data.model.WeatherDataResponse
import dev.agmzcr.weather.data.repository.WeatherRepository
import dev.agmzcr.weather.util.ApiException
import dev.agmzcr.weather.util.Event
import dev.agmzcr.weather.util.NoInternetException
import dev.agmzcr.weather.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<Event<State<WeatherDataResponse>>>()
    val weatherLiveData: LiveData<Event<State<WeatherDataResponse>>>
        get() = _weatherLiveData

    /*private val _forecastLiveData = MutableLiveData<Event<State<List<ForecastDataResponse.Forecast>>>>()
    val forecastLiveData: LiveData<Event<State<List<ForecastDataResponse.Forecast>>>>
        get() = _forecastLiveData*/

    private lateinit var weatherResponse: WeatherDataResponse
    private lateinit var forecastResponse: ForecastDataResponse.Forecast

    private val _currentLocation = MutableLiveData<Location>()

    fun fetchWeather(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                findCityWeather(cityName)
                //findCityForecast(cityName)
            }
        }
    }

    fun onLocationUpdated(location: Location) {
        _currentLocation.value = location
        fintWeatherByLocation()
    }

    private fun findCityWeather(cityName: String) {
        _weatherLiveData.postValue(Event(State.loading()))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                weatherResponse = repository.findCityWeather(cityName)
                _weatherLiveData.postValue(Event(State.success(weatherResponse)))
            } catch (e: ApiException) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            } catch (e: NoInternetException) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            }
        }
    }

    private fun fintWeatherByLocation() {
        _weatherLiveData.postValue(Event(State.loading()))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                weatherResponse = _currentLocation.value?.let { location ->
                    repository.findWeatherByLocation(location.latitude, location.longitude)
                }!!
                _weatherLiveData.postValue(Event(State.success(weatherResponse)))
            } catch (e: ApiException) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            } catch (e: NoInternetException) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _weatherLiveData.postValue(Event(State.error(e.message ?: "")))
                }
            }
        }
    }

    /*private fun findCityForecast(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dataList = repository.findCityForecast(cityName)
            withContext(Dispatchers.Main) {
                _forecastLiveData.postValue(Event(State.success(dataList.list)))
            }
        }
    }*/
}
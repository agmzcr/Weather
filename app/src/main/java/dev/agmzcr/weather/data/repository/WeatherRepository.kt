package dev.agmzcr.weather.data.repository

import dev.agmzcr.weather.data.model.ForecastDataResponse
import dev.agmzcr.weather.data.model.WeatherDataResponse
import dev.agmzcr.weather.data.network.ApiInterface
import dev.agmzcr.weather.data.network.SafeApiRequest
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: ApiInterface
) : SafeApiRequest() {

    suspend fun findCityWeather(cityName: String): WeatherDataResponse = apiRequest {
        api.findCityWeatherData(cityName)
    }

    suspend fun findCityForecast(cityName: String): ForecastDataResponse = apiRequest {
        api.findCityForecastData(cityName)
    }

    suspend fun findWeatherByLocation(lat: Double, lon: Double): WeatherDataResponse = apiRequest {
        api.findWeatherDataByLocation(lat, lon)
    }

    suspend fun findForecastByLocation(lat: Double, lon: Double): ForecastDataResponse = apiRequest {
        api.findForecastDataByLocation(lat, lon)
    }
}
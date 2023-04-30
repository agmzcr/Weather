package dev.agmzcr.weather.data.network

import dev.agmzcr.weather.data.model.ForecastDataResponse
import dev.agmzcr.weather.data.model.PhotosResponse
import dev.agmzcr.weather.data.model.WeatherDataResponse
import dev.agmzcr.weather.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    suspend fun findCityWeatherData(
        @Query("q") q: String,
        @Query("units") units: String = Constants.WEATHER_UNIT,
        @Query("appid") appid: String = Constants.API_KEY
    ): Response<WeatherDataResponse>

    @GET("weather")
    suspend fun findWeatherDataByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = Constants.WEATHER_UNIT,
        @Query("appid") appid: String = Constants.API_KEY
    ): Response<WeatherDataResponse>

    @GET("forecast")
    suspend fun findCityForecastData(
        @Query("q") q: String,
        @Query("units") units: String = Constants.WEATHER_UNIT,
        @Query("appid") appid: String = Constants.API_KEY
    ): Response<ForecastDataResponse>

    @GET("forecast")
    suspend fun findForecastDataByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = Constants.WEATHER_UNIT,
        @Query("appid") appid: String = Constants.API_KEY
    ): Response<ForecastDataResponse>

    @GET("search/photos")
    suspend fun findPhotos(
        @Query("client_id") client_id: String = Constants.API_KEY_PHOTOS,
        @Query("query") query: String
    ): Response<PhotosResponse>
}
package dev.agmzcr.weather.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastDataResponse(
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("message")
    val message: Int,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<Forecast>,
    @SerializedName("city")
    val city: City
) {
    @Keep
    data class City(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("country")
        val country: String,
        @SerializedName("population")
        val population: Int,
        @SerializedName("timezone")
        val timezone: Int,
        @SerializedName("sunrise")
        val sunrise: Long,
        @SerializedName("sunset")
        val sunset: Long
    )

    @Keep
    data class Coord(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    @Keep
    data class Forecast(
        @SerializedName("dt")
        val dt: Long,
        @SerializedName("main")
        val main: Main,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("dt_text")
        val dt_text: String
    )

    @Keep
    data class Main(
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("feels_like")
        val feels_like: Double,
        @SerializedName("temp_min")
        val temp_min: Double,
        @SerializedName("temp_max")
        val temp_max: Double,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("sea_level")
        val sea_level: Int,
        @SerializedName("grnd_level")
        val grnd_level: Int,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("temp_kf")
        val temp_kf: Double
    )

    @Keep
    data class Weather(
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String
    )

    @Keep
    data class Clouds(
        @SerializedName("all")
        val all: Int
    )

    @Keep
    data class Wind(
        @SerializedName("speed")
        val speed: Double,
        @SerializedName("deg")
        val deg: Int,
        @SerializedName("gust")
        val gust: Double
    )

    @Keep
    data class Sys(
        @SerializedName("pod")
        val pod: String
    )
}
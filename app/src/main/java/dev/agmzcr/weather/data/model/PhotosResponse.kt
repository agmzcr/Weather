package dev.agmzcr.weather.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PhotosResponse(
    @SerializedName("results")
    val results: List<Results>
)

@Keep
data class Results(
    @SerializedName("urls")
    val urls: Urls
)

@Keep
data class Urls(
    @SerializedName("raw")
    val raw: String,
    @SerializedName("full")
    val full: String,
    @SerializedName("regular")
    val regular: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("thumb")
    val thumb: String
)
